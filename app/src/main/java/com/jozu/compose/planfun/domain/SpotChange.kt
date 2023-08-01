package com.jozu.compose.planfun.domain

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class SpotChange(
    val type: Type,
    val spot: Spot,
) {
    enum class Type {
        ADDED,
        MODIFIED,
        REMOVED,
    }
}