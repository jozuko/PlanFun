package com.jozu.compose.planfun.presentation.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozu.compose.planfun.presentation.common.LoadingManager
import com.jozu.compose.planfun.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/07/31.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val signOutCase: SignOutUseCase,
) : ViewModel() {
    private val _displayScreen: MutableState<DisplayScreen> = mutableStateOf(DisplayScreen.SPOT)
    val displayScreen get() = _displayScreen

    fun onClickMenuPlan() {
        _displayScreen.value = DisplayScreen.PLAN
    }

    fun onClickMenuSpot() {
        _displayScreen.value = DisplayScreen.SPOT
    }

    fun onClickSignOut() {
        LoadingManager.showLoading()
        viewModelScope.launch {
            signOutCase.signOut()
            LoadingManager.hideLoading()
        }
    }
}

enum class DisplayScreen {
    PLAN, SPOT,
}