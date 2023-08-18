package com.jozu.compose.planfun.presentation.nav

/**
 *
 * Created by jozuko on 2023/08/18.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class AppNavRoute(val route: String) {
    object Main : AppNavRoute("app-main")
    object ShareTarget : AppNavRoute("app-share-target")
}