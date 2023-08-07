package com.jozu.compose.planfun.domain.image

/**
 *
 * Created by jozuko on 2023/08/04.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class ImageUploadStatus<out T> {
    object Start : ImageUploadStatus<Nothing>()
    data class Success(val value: Image) : ImageUploadStatus<Image>()
    data class Error(val error: Throwable) : ImageUploadStatus<Nothing>()
}