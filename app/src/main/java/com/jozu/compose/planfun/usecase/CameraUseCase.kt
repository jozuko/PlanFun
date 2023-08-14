package com.jozu.compose.planfun.usecase

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/14.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
class CameraUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun getImageUri(): Uri {
        val imagePath = File(context.cacheDir, "images")
        imagePath.mkdirs()

        val time = System.currentTimeMillis()
        val file = File(imagePath, "$time.jpg")
        return FileProvider.getUriForFile(
            context,
            context.packageName + ".fileprovider",
            file
        )
    }
}