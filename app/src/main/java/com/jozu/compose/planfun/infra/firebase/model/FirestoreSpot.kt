package com.jozu.compose.planfun.infra.firebase.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.jozu.compose.planfun.domain.spot.Spot

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
data class FirestoreSpot(
    @DocumentId val id: String? = null,
    val name: String? = null,
    val location: GeoPoint? = null,
    val address: String? = null,
    val tel: String? = null,
    val url: String? = null,
    val memo: String? = null,
    val imageName: String? = null,
) {
    companion object {
        fun fromInput(name: String, location: LatLng?, address: String, tel: String, url: String, memo: String, imageName: String): FirestoreSpot {
            return FirestoreSpot(
                id = null,
                name = name,
                location = location?.let { GeoPoint(it.latitude, it.longitude) },
                address = address,
                tel = tel,
                url = url,
                memo = memo,
                imageName = imageName,
            )
        }
    }

    fun toDomain(): Spot {
        return Spot(
            id = id,
            name = name ?: "",
            location = location?.let { LatLng(it.latitude, it.longitude) },
            address = address ?: "",
            tel = tel ?: "",
            url = url ?: "",
            memo = memo ?: "",
            imageName = imageName ?: "",
        )
    }
}