package com.jozu.compose.planfun.usecase

import com.jozu.compose.planfun.domain.spot.Spot
import com.jozu.compose.planfun.domain.spot.SpotRepository
import com.jozu.compose.planfun.domain.spot.SpotStatus
import kotlinx.coroutines.flow.last
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class SpotGetUseCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    suspend fun getInitialData(): SpotStatus<List<Spot>> {
        return spotRepository.getAll().last()
    }
}