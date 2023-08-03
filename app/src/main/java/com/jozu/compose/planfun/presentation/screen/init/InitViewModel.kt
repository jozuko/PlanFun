package com.jozu.compose.planfun.presentation.screen.init

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozu.compose.planfun.domain.ImageMergeStatus
import com.jozu.compose.planfun.usecase.ImageGetCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class InitViewModel @Inject constructor(
    private val imageGetCase: ImageGetCase,
) : ViewModel() {
    private val _statusFlow: MutableStateFlow<ImageMergeStatus> = MutableStateFlow(ImageMergeStatus.Start)
    val statusFlow = _statusFlow.asStateFlow()

    fun start() {
        viewModelScope.launch {
            imageGetCase.getInitialData().collectLatest { status ->
                Timber.d("InitViewModel start $status")
                _statusFlow.value = status
            }
        }
    }
}