package com.jozu.compose.planfun.usecase

import com.jozu.compose.planfun.domain.Spot
import com.jozu.compose.planfun.domain.SpotFuture
import com.jozu.compose.planfun.domain.SpotRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class SpotGetCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    suspend fun getInitialData(): SpotFuture<List<Spot>> {
        return spotRepository.getAll().first()
    }
}