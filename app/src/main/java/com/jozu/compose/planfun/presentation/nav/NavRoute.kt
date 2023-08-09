package com.jozu.compose.planfun.presentation.nav

/**
 *
 * Created by jozuko on 2023/08/09.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class NavRoute(val route: String) {
    object Plan : NavRoute("plan-group") {
        object PlanList : NavRoute("plan-list")
    }

    object Spot : NavRoute("spot-group") {
        object SpotList : NavRoute("spot-list")
        object SpotAdd : NavRoute("spot-add")
    }

    object Account : NavRoute("account-group") {
        object AccountMenu : NavRoute("account-menu")
    }
}