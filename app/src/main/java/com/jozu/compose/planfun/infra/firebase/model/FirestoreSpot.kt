package com.jozu.compose.planfun.infra.firebase.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.jozu.compose.planfun.domain.Spot

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
    val imageId: String? = null,
    val memo: List<String> = emptyList(),
) {
    companion object {
        fun fromSpot(spot: Spot): FirestoreSpot {
            return FirestoreSpot(
                id = spot.id,
                name = spot.name,
                location = spot.location?.let { GeoPoint(it.latitude, it.longitude) },
                address = spot.address,
                tel = spot.tel,
                url = spot.url,
                imageId = spot.photoId,
                memo = spot.memo.map { it },
            )
        }
    }

    fun toDomain(): Spot? {
        return Spot(
            id = id,
            name = name ?: "",
            location = location?.let { LatLng(it.latitude, it.longitude) } ?: return null,
            address = address ?: "",
            tel = tel ?: "",
            url = url ?: "",
            photoId = imageId ?: "",
            memo = memo.map { it },
        )
    }
}