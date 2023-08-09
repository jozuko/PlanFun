package com.jozu.compose.planfun.presentation.nav

import androidx.compose.ui.graphics.vector.ImageVector

/**
 *
 * Created by jozuko on 2023/08/09.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    val route: String,
    val startDestination: String,
)