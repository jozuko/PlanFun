package com.jozu.compose.planfun.presentation.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Festival
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.outlined.Festival
import androidx.compose.material.icons.outlined.ListAlt
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jozu.compose.planfun.R

/**
 * NavigationBar＋NavHostの実装
 * https://zenn.dev/ykrods/articles/580bc1fda58081
 *
 * Created by jozuko on 2023/08/09.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
private val barItems = listOf(
    BottomNavItem(
        label = R.string.tab_menu_plan,
        icon = Icons.Outlined.ListAlt,
        iconSelected = Icons.Filled.ListAlt,
        route = NavRoute.Plan.route,
        startDestination = NavRoute.Plan.PlanList.route,
    ),
    BottomNavItem(
        label = R.string.tab_menu_spot,
        icon = Icons.Outlined.Festival,
        iconSelected = Icons.Filled.Festival,
        route = NavRoute.Spot.route,
        startDestination = NavRoute.Spot.SpotList.route,
    ),
    BottomNavItem(
        label = R.string.tab_menu_account,
        icon = Icons.Outlined.ManageAccounts,
        iconSelected = Icons.Filled.ManageAccounts,
        route = NavRoute.Account.route,
        startDestination = NavRoute.Account.AccountMenu.route,
    ),
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    NavigationBar {
        barItems.forEach { bottomNavItem ->
            val selected = currentDestination?.hierarchy?.any { it.route == bottomNavItem.route } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    val targetRoute = if (selected) bottomNavItem.startDestination else bottomNavItem.route
                    navController.navigate(targetRoute) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(stringResource(bottomNavItem.label)) },
                icon = {
                    val imageVector = if (selected) {
                        bottomNavItem.iconSelected
                    } else {
                        bottomNavItem.icon
                    }
                    Icon(imageVector, contentDescription = null)
                },
            )
        }
    }
}