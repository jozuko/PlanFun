package com.jozu.compose.planfun.presentation.nav

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jozu.compose.planfun.presentation.screen.account.AccountMenuScreen
import com.jozu.compose.planfun.presentation.screen.plan.PlanListScreen
import com.jozu.compose.planfun.presentation.screen.spot.SpotAddScreen
import com.jozu.compose.planfun.presentation.screen.spot.SpotListScreen

/**
 * NavigationBar＋NavHostの実装
 * https://zenn.dev/ykrods/articles/580bc1fda58081
 *
 * Created by jozuko on 2023/08/09.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRoute.Plan.route) {
        navigation(startDestination = NavRoute.Plan.PlanList.route, route = NavRoute.Plan.route) {
            navComposable(NavRoute.Plan.PlanList.route) {
                PlanListScreen()
            }
        }

        navigation(startDestination = NavRoute.Spot.SpotList.route, route = NavRoute.Spot.route) {
            navComposable(NavRoute.Spot.SpotList.route) {
                SpotListScreen(onNavigateToSpotAdd = {
                    navController.navigate(NavRoute.Spot.SpotAdd.route)
                })
            }
            navComposable(NavRoute.Spot.SpotAdd.route) {
                SpotAddScreen(onNavigateToBack = {
                    navController.popBackStack()
                })
            }
        }

        navigation(startDestination = NavRoute.Account.AccountMenu.route, route = NavRoute.Account.route) {
            navComposable(NavRoute.Account.AccountMenu.route) {
                AccountMenuScreen()
            }
        }
    }
}

private fun NavGraphBuilder.navComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = NavAnim.enterTransition,
        exitTransition = NavAnim.exitTransition,
        popEnterTransition = NavAnim.popEnterTransition,
        popExitTransition = NavAnim.popExitTransition,
        content = content,
    )
}