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
) {
    companion object {
        fun newSpot(
            name: String,
            location: LatLng?,
            address: String,
            tel: String,
            url: String,
            imageName: String?,
            memo: String,
        ): Spot {
            return Spot(
                id = null,
                name = name,
                location = location,
                address = address,
                tel = tel,
                url = url,
                imageName = imageName,
                memo = memo,
            )
        }
    }
}