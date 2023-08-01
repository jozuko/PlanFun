package com.jozu.compose.planfun.domain

import kotlinx.coroutines.flow.Flow

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
interface SpotRepository {
    val spotListFlow: Flow<List<SpotChange>>
    fun getAll(): Flow<SpotFuture<List<Spot>>>
    fun add(spot: Spot): Flow<SpotFuture<Spot>>
}