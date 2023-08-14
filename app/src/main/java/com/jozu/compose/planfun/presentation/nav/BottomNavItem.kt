package com.jozu.compose.planfun.presentation.nav

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

/**
 *
 * Created by jozuko on 2023/08/09.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class BottomNavItem(
    @StringRes val label: Int,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    val route: String,
    val startDestination: String,
)