package com.jozu.compose.planfun.presentation.common

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

/**
 *
 * Created by jozuko on 2023/07/31.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
object LoadingManager {
    private val _isLoading = MutableStateFlow(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun showLoading() {
        Timber.d("Loading show")
        _isLoading.value = true
    }

    fun hideLoading() {
        Timber.d("Loading hide")
        _isLoading.value = false
    }
}