package com.jozu.compose.planfun.domain.spot

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
interface SpotRepository {
    val spotListFlow: Flow<List<SpotChange>>
    fun getAll(): Flow<SpotStatus<List<Spot>>>
    fun add(name: String, location: LatLng?, address: String, tel: String, url: String, memo: String, imageName: String): Flow<SpotStatus<String>>
}