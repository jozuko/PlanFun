package com.jozu.compose.planfun.di

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.jozu.compose.planfun.domain.AccountRepository
import com.jozu.compose.planfun.infra.firebase.AccountRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAccountRepository(
        auth: FirebaseAuth,
        googleSignInClient: GoogleSignInClient,
    ): AccountRepository = AccountRepositoryImpl(Dispatchers.IO, auth, googleSignInClient)
}