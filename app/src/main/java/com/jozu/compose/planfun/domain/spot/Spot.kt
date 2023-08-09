package com.jozu.compose.planfun.domain.spot

import com.google.android.gms.maps.model.LatLng

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class Spot(
    val id: String?,
    val name: String,
    val location: LatLng?,
    val address: String,
    val tel: String,
    val url: String,
    val imageName: String?,
    val memo: String,
)