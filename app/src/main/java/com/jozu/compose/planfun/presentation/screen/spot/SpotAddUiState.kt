package com.jozu.compose.planfun.presentation.screen.spot

import androidx.compose.runtime.Stable
import com.google.android.gms.maps.model.LatLng
import com.jozu.compose.planfun.domain.spot.Spot
import java.io.File

/**
 *
 * Created by jozuko on 2023/08/04.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Stable
data class SpotAddUiState(
    val image: File? = null,
    val name: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val address: String = "",
    val tel: String = "",
    val url: String = "",
    val memo: String = "",
    val visible: Boolean = true,
) {
    fun updateInput(inputField: InputField, newValue: String): SpotAddUiState {
        return when (inputField) {
            InputField.NAME -> copy(name = newValue)
            InputField.LATITUDE -> copy(latitude = newValue)
            InputField.LONGITUDE -> copy(longitude = newValue)
            InputField.ADDRESS -> copy(address = newValue)
            InputField.TEL -> copy(tel = newValue)
            InputField.URL -> copy(url = newValue)
            InputField.MEMO -> copy(memo = newValue)
        }
    }

    fun updateVisibility(visible: Boolean): SpotAddUiState {
        return copy(visible = visible)
    }

    val toDomain: Spot
        get() {
            val lat = latitude.toDoubleOrNull()
            val lon = longitude.toDoubleOrNull()
            val location = if (lat != null && lon != null) LatLng(lat, lon) else null

            return Spot.newSpot(
                name = name,
                location = location,
                address = address,
                tel = tel,
                url = url,
                imageName = null,
                memo = memo,
            )
        }
}

enum class InputField {
    NAME,
    LATITUDE,
    LONGITUDE,
    ADDRESS,
    TEL,
    URL,
    MEMO,
}