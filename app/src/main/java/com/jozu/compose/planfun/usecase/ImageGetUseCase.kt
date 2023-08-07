package com.jozu.compose.planfun.usecase

import com.jozu.compose.planfun.domain.image.ImageMergeStatus
import com.jozu.compose.planfun.domain.image.ImageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class ImageGetUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    fun getInitialData(): Flow<ImageMergeStatus> {
        return imageRepository.merge()
    }
}