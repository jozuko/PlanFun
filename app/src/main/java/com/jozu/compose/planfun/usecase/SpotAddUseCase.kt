package com.jozu.compose.planfun.usecase

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.jozu.compose.planfun.domain.image.Image
import com.jozu.compose.planfun.domain.image.ImageRepository
import com.jozu.compose.planfun.domain.image.ImageUploadStatus
import com.jozu.compose.planfun.domain.spot.SpotRepository
import kotlinx.coroutines.flow.last
import timber.log.Timber
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class SpotAddUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
    private val spotRepository: SpotRepository,
) {
    suspend fun add(
        name: String,
        location: LatLng?,
        address: String,
        tel: String,
        url: String,
        memo: String,
        image: Bitmap?,
    ) {
        val imageName = uploadImage(image)?.name ?: ""
        spotRepository.add(name, location, address, tel, url, memo, imageName).last()
    }

    private suspend fun uploadImage(photo: Bitmap?): Image? {
        photo ?: return null

        val imageUploadStatus = imageRepository.upload(photo).last()
        if (imageUploadStatus is ImageUploadStatus.Error) {
            throw imageUploadStatus.error
        }

        if (imageUploadStatus !is ImageUploadStatus.Success) {
            return null
        }

        Timber.d("image uploaded ${imageUploadStatus.value.name}")
        return imageUploadStatus.value
    }
}