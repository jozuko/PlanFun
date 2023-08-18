package com.jozu.compose.planfun.presentation.nav

/**
 *
 * Created by jozuko on 2023/08/09.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class HomeNavRoute(val route: String) {
    object Plan : HomeNavRoute("plan-group") {
        object PlanList : HomeNavRoute("plan-list")
    }

    object Spot : HomeNavRoute("spot-group") {
        object SpotList : HomeNavRoute("spot-list")
        object SpotAdd : HomeNavRoute("spot-add")
    }

    object Account : HomeNavRoute("account-group") {
        object AccountMenu : HomeNavRoute("account-menu")
    }
}