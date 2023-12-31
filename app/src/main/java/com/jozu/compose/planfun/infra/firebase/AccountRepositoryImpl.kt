package com.jozu.compose.planfun.infra.firebase

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jozu.compose.planfun.domain.account.Account
import com.jozu.compose.planfun.domain.account.AccountRepository
import com.jozu.compose.planfun.domain.account.AccountStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/07/21.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class AccountRepositoryImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
) : AccountRepository {
    override val accountFuture: Flow<AccountStatus<Account>>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                Timber.d("FirebaseAuth.AuthStateListener called. user=${auth.currentUser?.uid}")

                val firebaseUser = auth.currentUser
                if (firebaseUser == null) {
                    this.trySend(AccountStatus.Unauthorized)
                } else {
                    this.trySend(AccountStatus.Authorized(Account.fromFirebaseUser(firebaseUser)))
                }
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override fun requestGoogleAuth(): Intent {
        return googleSignInClient.signInIntent
    }

    override suspend fun signInGoogle(resultData: Intent): Unit = withContext(dispatcher) {
        Timber.d("<AccountRepository>signInGoogle start")
        val googleSignInAccount: GoogleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(resultData).await()
        val firebaseCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        auth.signInWithCredential(firebaseCredential).await()
        Timber.d("<AccountRepository>signInGoogle end")
    }

    override suspend fun signOut(): Unit = withContext(dispatcher) {
        Timber.d("<AccountRepository>signOut start")
        val firebaseUser = auth.currentUser ?: return@withContext
        if (firebaseUser.providerData.any { it.providerId == "google.com" }) {
            googleSignInClient.signOut().await()
        }

        auth.signOut()
        Timber.d("<AccountRepository>signOut end")
    }
}