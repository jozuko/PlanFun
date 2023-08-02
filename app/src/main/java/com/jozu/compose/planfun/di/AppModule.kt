package com.jozu.compose.planfun.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.jozu.compose.planfun.domain.AccountRepository
import com.jozu.compose.planfun.domain.ImageRepository
import com.jozu.compose.planfun.domain.SpotRepository
import com.jozu.compose.planfun.infra.firebase.AccountRepositoryImpl
import com.jozu.compose.planfun.infra.firebase.ImageRepositoryImpl
import com.jozu.compose.planfun.infra.firebase.SpotRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideSpotRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): SpotRepository = SpotRepositoryImpl(Dispatchers.IO, auth, firestore)

    @Provides
    fun provideImageRepository(
        @ApplicationContext context: Context,
        auth: FirebaseAuth,
        storage: FirebaseStorage,
    ): ImageRepository = ImageRepositoryImpl(context, Dispatchers.IO, auth, storage)
}