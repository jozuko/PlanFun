package com.jozu.compose.planfun.presentation.common.composable

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.drawToBitmap

/**
 * https://mobileappcircular.com/jetpack-composable-to-bitmap-image-4ddea2fe3238
 */
@Composable
fun captureBitmap(content: @Composable () -> Unit): () -> Bitmap {
    val context = LocalContext.current
    val composeView = remember { ComposeView(context) }
    fun captureBitmap(): Bitmap {
        return composeView.drawToBitmap()
    }
    AndroidView(
        factory = {
            composeView.apply {
                setContent {
                    content.invoke()
                }
            }
        }
    )
    return ::captureBitmap
}