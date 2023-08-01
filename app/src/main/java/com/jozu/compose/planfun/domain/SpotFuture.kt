package com.jozu.compose.planfun.domain

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class SpotFuture<out T> {
    object Proceeding : SpotFuture<Nothing>()
    data class Success<out T>(val value: T) : SpotFuture<T>()
    data class Error(val error: Throwable) : SpotFuture<Nothing>()
}