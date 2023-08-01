package com.jozu.compose.planfun.domain

import java.util.Calendar

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class PlanSpot(
    val spot: Spot,
    val start: Calendar,
    val stayMinute: Int,
) : PlanItem