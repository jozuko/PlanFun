package com.jozu.compose.planfun.presentation.screen.signin

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozu.compose.planfun.presentation.common.LoadingManager
import com.jozu.compose.planfun.usecase.GoogleSignInCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/07/31.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleSignInCase: GoogleSignInCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    fun onClickSignIn(launcher: ActivityResultLauncher<Intent>) {
        LoadingManager.showLoading()
        viewModelScope.launch {
            googleSignInCase.signIn(launcher)
        }
    }

    fun onResultSignIn(result: ActivityResult) {
        viewModelScope.launch {
            googleSignInCase.onResultSignIn(result)
            LoadingManager.hideLoading()
        }
    }
}