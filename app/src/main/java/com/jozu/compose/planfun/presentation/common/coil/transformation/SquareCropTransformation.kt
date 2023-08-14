package com.jozu.compose.planfun.presentation.common.coil.transformation

import android.graphics.Bitmap
import android.graphics.Paint
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import coil.size.Size
import coil.transform.Transformation

/**
 *
 * Created by jozuko on 2023/08/14.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class SquareCropTransformation : Transformation {
    override val cacheKey: String = javaClass.name

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

        val minSize = minOf(input.width, input.height)
        val output = createBitmap(minSize, minSize, input.config ?: Bitmap.Config.ARGB_8888)

        val left = -(input.width - minSize) / 2f
        val top = -(input.height - minSize) / 2f

        output.applyCanvas {
            drawBitmap(input, left, top, paint)
        }

        if (!input.isRecycled) {
            input.recycle()
        }

        return output
    }
}