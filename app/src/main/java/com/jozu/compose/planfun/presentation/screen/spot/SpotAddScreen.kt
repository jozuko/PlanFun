package com.jozu.compose.planfun.presentation.screen.spot

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jozu.compose.planfun.R
import com.jozu.compose.planfun.presentation.theme.roundedMiddle
import java.io.File

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpotAddScreen(onDismissRequest: () -> Unit, viewModel: SpotAddViewModel = hiltViewModel()) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
            AsyncImage(
                model = photoImage(LocalContext.current, viewModel.spotImage.value),
                placeholder = painterResource(id = R.mipmap.photo_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.clip(RoundedCornerShape(roundedMiddle))
            )
        }
    }
}

private fun photoImage(context: Context, spotImage: File?): ImageRequest {
    return ImageRequest.Builder(context)
        .data(spotImage ?: R.mipmap.photo_placeholder)
        .build()
}