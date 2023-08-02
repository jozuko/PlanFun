package com.jozu.compose.planfun.presentation.screen.init

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jozu.compose.planfun.R
import com.jozu.compose.planfun.domain.ImageMergeStatus
import com.jozu.compose.planfun.presentation.screen.home.HomeScreen
import com.jozu.compose.planfun.presentation.screen.splash.SplashScreen
import com.jozu.compose.planfun.presentation.theme.paddingLarge

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun InitScreen(initViewModel: InitViewModel = hiltViewModel()) {
    val status = initViewModel.statusFlow.collectAsState().value

    LaunchedEffect(Unit) {
        initViewModel.start()
    }

    when (status) {
        is ImageMergeStatus.Start -> {
            WorkingScreen(stringResource(id = R.string.init_image_merge_start))
        }

        is ImageMergeStatus.ServerNamesDone -> {
            WorkingScreen(stringResource(id = R.string.init_image_merge_server_names_done))
        }

        is ImageMergeStatus.LocalFilesDone -> {
            WorkingScreen(stringResource(id = R.string.init_image_merge_local_files_done))
        }

        is ImageMergeStatus.DownloadDone -> {
            WorkingScreen(stringResource(id = R.string.init_image_merge_download_done))
        }

        is ImageMergeStatus.RemoveDone -> {
            WorkingScreen(stringResource(id = R.string.init_image_merge_remove_done))
        }

        is ImageMergeStatus.Done -> {
            HomeScreen()
        }

        is ImageMergeStatus.Error -> {
            WorkingScreen(stringResource(id = R.string.init_image_merge_error))
        }
    }
}

@Composable
private fun WorkingScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        SplashScreen()
        Text(
            message,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = paddingLarge),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}