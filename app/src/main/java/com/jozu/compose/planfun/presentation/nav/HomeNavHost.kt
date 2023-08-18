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
fun HomeNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = HomeNavRoute.Plan.route,
        route = "home-nav"
    ) {
        navigation(startDestination = HomeNavRoute.Plan.PlanList.route, route = HomeNavRoute.Plan.route) {
            navComposable(HomeNavRoute.Plan.PlanList.route) {
                PlanListScreen()
            }
        }

        navigation(startDestination = HomeNavRoute.Spot.SpotList.route, route = HomeNavRoute.Spot.route) {
            navComposable(HomeNavRoute.Spot.SpotList.route) {
                SpotListScreen(onNavigateToSpotAdd = {
                    navController.navigate(HomeNavRoute.Spot.SpotAdd.route)
                })
            }
            navComposable(HomeNavRoute.Spot.SpotAdd.route) {
                SpotAddScreen(onNavigateToBack = {
                    navController.popBackStack()
                })
            }
        }

        navigation(startDestination = HomeNavRoute.Account.AccountMenu.route, route = HomeNavRoute.Account.route) {
            navComposable(HomeNavRoute.Account.AccountMenu.route) {
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