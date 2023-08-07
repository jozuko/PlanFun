package com.jozu.compose.planfun.presentation.screen.spot

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozu.compose.planfun.presentation.common.LoadingManager
import com.jozu.compose.planfun.usecase.SpotAddUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class SpotAddViewModel @Inject constructor(
    private val spotAddCase: SpotAddUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SpotAddUiState())
    val uiState = _uiState.asStateFlow()

    fun onChangeInput(inputField: InputField, newValue: String) {
        _uiState.update {
            it.updateInput(inputField, newValue)
        }
    }

    fun changeVisibility(visibility: Boolean) {
        _uiState.update {
            it.updateVisibility(visibility)
        }
    }

    fun add(snapshot: (() -> Bitmap)?) {
        LoadingManager.showLoading()
        viewModelScope.launch {
            val newSpot = _uiState.value.toDomain
            spotAddCase.add(
                name = newSpot.name,
                location = newSpot.location,
                address = newSpot.address,
                tel = newSpot.tel,
                url = newSpot.url,
                memo = newSpot.memo,
                photo = snapshot?.invoke(),
            )
            LoadingManager.hideLoading()
            changeVisibility(false)
        }
    }

    fun cancel() {
        changeVisibility(false)
    }
}