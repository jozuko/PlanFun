package com.jozu.compose.planfun.domain

import kotlinx.coroutines.flow.Flow

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
interface ImageRepository {
    fun merge(): Flow<ImageMergeStatus>
}