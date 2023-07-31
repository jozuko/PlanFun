package com.jozu.compose.planfun.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozu.compose.planfun.presentation.common.LoadingManager
import com.jozu.compose.planfun.usecase.SignOutCase
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
    private val signOutCase: SignOutCase,
) : ViewModel() {
    fun onClickSignOut() {
        LoadingManager.showLoading()
        viewModelScope.launch {
            signOutCase.signOut()
            LoadingManager.hideLoading()
        }
    }
}