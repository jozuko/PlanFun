package com.jozu.compose.planfun.infra.firebase

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jozu.compose.planfun.domain.Account
import com.jozu.compose.planfun.domain.AccountFuture
import com.jozu.compose.planfun.domain.AccountRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 *
 * Created by jozuko on 2023/07/21.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class AccountRepositoryImpl(
    private val coroutineContext: CoroutineContext,
    private val auth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient,
) : AccountRepository {
    override val accountFuture: Flow<AccountFuture<Account>>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                Timber.d("FirebaseAuth.AuthStateListener called. user=${auth.currentUser?.uid}")

                val firebaseUser = auth.currentUser
                if (firebaseUser == null) {
                    this.trySend(AccountFuture.Unauthorized)
                } else {
                    this.trySend(AccountFuture.Authorized(Account.fromFirebaseUser(firebaseUser)))
                }
            }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override fun requestGoogleAuth(): Intent {
        return googleSignInClient.signInIntent
    }

    override suspend fun signInGoogle(resultData: Intent): Unit = withContext(coroutineContext) {
        val googleSignInAccount: GoogleSignInAccount = GoogleSignIn.getSignedInAccountFromIntent(resultData).await()
        val firebaseCredential = GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)
        auth.signInWithCredential(firebaseCredential).await()
    }

    override suspend fun signOut(): Unit = withContext(coroutineContext) {
        val firebaseUser = auth.currentUser ?: return@withContext
        if (firebaseUser.providerData.any { it.providerId == "google.com" }) {
            googleSignInClient.signOut().await()
        }

        auth.signOut()
    }
}