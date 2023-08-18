package com.jozu.compose.planfun.presentation.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.jozu.compose.planfun.presentation.nav.AppNavHost
import com.jozu.compose.planfun.presentation.theme.PlanFunTheme

/**
 *
 * Created by jozuko on 2023/07/29.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun PlanFunApp() {
    val navController = rememberNavController()

    PlanFunTheme {
        AppNavHost(navController)
    }
}
