package com.jozu.compose.planfun.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 *
 * Created by jozuko on 2023/08/04.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
object ProvideModule {
    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}