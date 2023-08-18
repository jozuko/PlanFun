package com.jozu.compose.planfun.domain.share_target

/**
 *
 * Created by jozuko on 2023/08/18.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class SharedContent<String> {
    object Proceeding : SharedContent<String>()
    data class Analyzed(val result: String) : SharedContent<String>()
    data class Error(val cause: Throwable) : SharedContent<String>()
}