package com.jozu.compose.planfun.di

import com.jozu.compose.planfun.domain.account.AccountRepository
import com.jozu.compose.planfun.domain.image.ImageRepository
import com.jozu.compose.planfun.domain.spot.SpotRepository
import com.jozu.compose.planfun.infra.firebase.AccountRepositoryImpl
import com.jozu.compose.planfun.infra.firebase.ImageRepositoryImpl
import com.jozu.compose.planfun.infra.firebase.SpotRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    abstract fun provideAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    abstract fun provideSpotRepository(impl: SpotRepositoryImpl): SpotRepository

    @Binds
    abstract fun provideImageRepository(impl: ImageRepositoryImpl): ImageRepository
}