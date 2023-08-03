package com.jozu.compose.planfun.presentation.screen.signin

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jozu.compose.planfun.R
import com.jozu.compose.planfun.presentation.theme.paddingLarge

/**
 *
 * Created by jozuko on 2023/07/31.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun SignInScreen() {
    Scaffold(
        topBar = { AppBar() },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Content()
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar() {
    return CenterAlignedTopAppBar(
        title = {
            Text(stringResource(id = R.string.app_name))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.inversePrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.inversePrimary,
            actionIconContentColor = MaterialTheme.colorScheme.inversePrimary,
            scrolledContainerColor = MaterialTheme.colorScheme.primary,
        ),
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    )
}

@Composable
private fun Content(viewModel: SignInViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(id = R.string.signin_title), style = MaterialTheme.typography.titleLarge)
        Text(stringResource(id = R.string.signin_message), style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(paddingLarge))

        val signInLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = viewModel::onResultSignIn,
        )
        SignInWithGoogleButton(
            enabled = true,
            onClick = { viewModel.onClickSignIn(signInLauncher) },
        )
        Text(uiState.value.message)
    }
}
