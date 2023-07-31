package com.jozu.compose.planfun.presentation.screen.signin

/**
 *
 * Created by jozuko on 2023/07/31.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class SignInUiState(
    val isProceeding: Boolean = false,
    val message: String = "",
)