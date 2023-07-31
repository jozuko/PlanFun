package com.jozu.compose.planfun.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jozu.compose.planfun.domain.AccountFuture
import com.jozu.compose.planfun.presentation.common.LoadingManager
import com.jozu.compose.planfun.presentation.screen.home.HomeScreen
import com.jozu.compose.planfun.presentation.screen.signin.SignInScreen
import com.jozu.compose.planfun.presentation.theme.PlanFunTheme

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun PlanFunApp(viewModel: PlanFunAppViewModel = hiltViewModel()) {
    val accountState = viewModel.accountState.collectAsState()
    val isLoadingState = LoadingManager.isLoading.collectAsState()

    PlanFunTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Scaffold { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                ) {
                    val contentBoxModifier = if (isLoadingState.value) {
                        Modifier.blur(
                            radiusX = 10.dp,
                            radiusY = 10.dp,
                            edgeTreatment = BlurredEdgeTreatment.Rectangle,
                        )
                    } else {
                        Modifier
                    }.fillMaxSize()

                    Box(modifier = contentBoxModifier) {
                        when (accountState.value) {
                            is AccountFuture.Authorized -> {
                                AuthorizedView()
                            }

                            is AccountFuture.Unauthorized -> {
                                UnauthorizedView()
                            }

                            is AccountFuture.Proceeding -> {
                                AuthProceedingView()
                            }

                            is AccountFuture.Error -> {
                                AuthErrorView()
                            }
                        }
                    }

                    if (isLoadingState.value) {
                        Loading()
                    }
                }
            }
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { },
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun AuthorizedView() {
    HomeScreen()
}

@Composable
private fun UnauthorizedView() {
    SignInScreen()
}

@Composable
private fun AuthProceedingView() {
    Text("auth Proceeding")
}

@Composable
private fun AuthErrorView() {
    Text("auth Error")
}