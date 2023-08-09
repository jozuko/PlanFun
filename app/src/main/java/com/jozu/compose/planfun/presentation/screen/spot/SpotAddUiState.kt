package com.jozu.compose.planfun.presentation.screen.spot

import androidx.compose.runtime.Stable
import com.google.android.gms.maps.model.LatLng

/**
 *
 * Created by jozuko on 2023/08/04.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Stable
data class SpotAddUiState(
    val image: String = "",
    val imageAngle: Float = 0f,
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

    fun rotateImage(): SpotAddUiState {
        return copy(imageAngle = imageAngle + 90f)
    }

    val location: LatLng?
        get() {
            val lat = latitude.toDoubleOrNull() ?: return null
            val lon = longitude.toDoubleOrNull() ?: return null
            return LatLng(lat, lon)
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