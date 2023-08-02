package com.jozu.compose.planfun.usecase

import com.google.android.gms.maps.model.LatLng
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
class SpotAddCase @Inject constructor(
    private val spotRepository: SpotRepository,
) {
    suspend fun add(): SpotFuture<Spot> {
        return spotRepository.add(
            Spot.newSpot(
                name = "name",
                location = LatLng(35.6809591, 139.7673068),
                address = "address",
                tel = "tel",
                url = "url",
                photoId = null,
                memo = listOf("memo"),
            )
        ).first()
    }
}