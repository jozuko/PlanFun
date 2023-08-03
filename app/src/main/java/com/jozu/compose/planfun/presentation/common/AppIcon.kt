package com.jozu.compose.planfun.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jozu.compose.planfun.R
import com.jozu.compose.planfun.presentation.theme.AppMainColor

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun BoxScope.AppIcon() {
    Box(
        modifier = Modifier
            .size(194.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AppMainColor)
            .align(Alignment.Center)
    ) {
        Image(
            modifier = Modifier.requiredSize(320.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(id = R.string.app_name),
        )
    }
}