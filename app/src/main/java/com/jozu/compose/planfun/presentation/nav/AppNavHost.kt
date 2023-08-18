package com.jozu.compose.planfun.presentation.nav

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navOptions
import com.jozu.compose.planfun.presentation.screen.AppMainScreen
import com.jozu.compose.planfun.presentation.screen.share_target.ShareTargetScreen
import timber.log.Timber

/**
 *
 * Created by jozuko on 2023/08/18.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun AppNavHost(navController: NavHostController) {
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
        Timber.d("OnDestinationChangedListener destination=${destination.route} previousBackStackEntry=${controller.previousBackStackEntry?.destination?.route}")

        if (destination.route == AppNavRoute.ShareTarget.route) {
            if (controller.previousBackStackEntry?.destination?.route == AppNavRoute.Main.route) {
                val routeId = controller.graph.findNode(AppNavRoute.ShareTarget.route)?.id ?: return@addOnDestinationChangedListener
                val navOptions = navOptions {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }

                controller.navigate(
                    resId = routeId,
                    args = arguments,
                    navOptions = navOptions,
                )
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = AppNavRoute.Main.route,
        route = "app-nav",
    ) {
        composable(
            route = AppNavRoute.Main.route,
        ) {
            AppMainScreen()
        }

        composable(
            route = AppNavRoute.ShareTarget.route,
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_SEND
                    mimeType = "text/*"
                },
            ),
        ) {
            ShareTargetScreen()
        }
    }
}