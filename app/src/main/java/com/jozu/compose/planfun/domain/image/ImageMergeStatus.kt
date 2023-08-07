package com.jozu.compose.planfun.domain.image

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
sealed class ImageMergeStatus {
    object Start : ImageMergeStatus()
    object ServerNamesDone : ImageMergeStatus()
    object LocalFilesDone : ImageMergeStatus()
    object DownloadDone : ImageMergeStatus()
    object RemoveDone : ImageMergeStatus()
    object Done : ImageMergeStatus()
    data class Error(val error: Throwable) : ImageMergeStatus()
}