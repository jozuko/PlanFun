package com.jozu.compose.planfun.presentation.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jozu.compose.planfun.presentation.common.AppIcon

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun BoxScope.SplashScreen() {
    AppIcon()
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewSplashScreen() {
    Box {
        SplashScreen()
    }
}