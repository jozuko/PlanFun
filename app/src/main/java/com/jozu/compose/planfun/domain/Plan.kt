package com.jozu.compose.planfun.domain

import java.util.Calendar

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class Plan(
    val name: String,
    val start: Calendar,
    val spot: List<PlanItem>,
)