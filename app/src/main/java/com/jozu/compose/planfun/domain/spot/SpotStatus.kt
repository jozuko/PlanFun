package com.jozu.compose.planfun.domain.spot

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class SpotStatus<out T> {
    object Proceeding : SpotStatus<Nothing>()
    data class Success<out T>(val value: T) : SpotStatus<T>()
    data class Error(val error: Throwable) : SpotStatus<Nothing>()
}