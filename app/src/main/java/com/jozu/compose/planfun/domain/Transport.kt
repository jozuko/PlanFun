package com.jozu.compose.planfun.domain

import com.jozu.compose.planfun.domain.plan.PlanItem

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class Transport(
    val way: TransportWay,
    val minute: Int,
) : PlanItem

