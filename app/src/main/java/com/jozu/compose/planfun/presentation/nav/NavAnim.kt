package com.jozu.compose.planfun.presentation.nav

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

/**
 *
 * Created by jozuko on 2023/08/09.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
object NavAnim {
    val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { fadeIn(initialAlpha = 0f) }

    val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { fadeOut(targetAlpha = 0.1f) }

    val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?
        get() = { fadeIn(initialAlpha = 0f) }

    val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?
        get() = { fadeOut(targetAlpha = 0.1f) }
}