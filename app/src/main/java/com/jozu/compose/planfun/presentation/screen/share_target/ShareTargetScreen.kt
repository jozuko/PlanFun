package com.jozu.compose.planfun.presentation.screen.share_target

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.jozu.compose.planfun.domain.share_target.SharedContent
import timber.log.Timber

/**
 *
 * Created by jozuko on 2023/08/18.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun ShareTargetScreen(viewModel: ShareTargetViewModel = hiltViewModel()) {
    Timber.d("Shared-Target!!")
    val sharedContentState = viewModel.sharedContentState.collectAsState()

    when (val sharedContent = sharedContentState.value) {
        is SharedContent.Proceeding -> {
            Text("Shared-Target Proceeding...")
        }

        is SharedContent.Analyzed -> {
            Text("Analyzed!! ${sharedContent.result}")
        }

        is SharedContent.Error -> {
            Text("Error!! ${sharedContent.cause.localizedMessage}")
        }
    }
}