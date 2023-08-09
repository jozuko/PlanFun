package com.jozu.compose.planfun.presentation.screen.spot

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RotateRight
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jozu.compose.planfun.R
import com.jozu.compose.planfun.presentation.common.composable.MultiLineTextField
import com.jozu.compose.planfun.presentation.common.composable.SingleLineTextField
import com.jozu.compose.planfun.presentation.common.composable.captureBitmap
import com.jozu.compose.planfun.presentation.theme.paddingMiddle
import com.jozu.compose.planfun.presentation.theme.paddingSmall
import com.jozu.compose.planfun.presentation.theme.roundedMiddle
import timber.log.Timber

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun SpotAddScreen(onNavigateToBack: () -> Unit, viewModel: SpotAddViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Contents(uiState, onNavigateToBack)
        }
    }

//    val modalSheetState = rememberModalBottomSheetState()
//    AlertDialog(
//        onDismissRequest = onDismissRequest,
//    ) {
//        Contents(uiState)
//    }
//    ModalBottomSheet(
//        onDismissRequest = {},
//        sheetState = modalSheetState,
//        shape = RoundedCornerShape(topStart = roundedLarge, topEnd = roundedLarge),
//        content = {
//            Text(text = "BottomSheet!!")
//        },
//    )
}

@Composable
private fun Contents(uiState: SpotAddUiState, onNavigateToBack: () -> Unit, viewModel: SpotAddViewModel = hiltViewModel()) {
    var snapshot: (() -> Bitmap)? = null
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(paddingMiddle)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            snapshot = photoImage(uiState, onClickImageRotate = viewModel::rotateImage)
            SpotName(uiState) { viewModel.onChangeInput(InputField.NAME, it) }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            SpotLatitude(uiState) { viewModel.onChangeInput(InputField.LATITUDE, it) }
            SpotLongitude(uiState) { viewModel.onChangeInput(InputField.LONGITUDE, it) }
        }

        SpotAddress(uiState) { viewModel.onChangeInput(InputField.ADDRESS, it) }
        SpotUrl(uiState) { viewModel.onChangeInput(InputField.URL, it) }
        SpotTel(uiState) { viewModel.onChangeInput(InputField.TEL, it) }
        SpotMemo(uiState) { viewModel.onChangeInput(InputField.MEMO, it) }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            CancelButton(onClick = { viewModel.cancel(onNavigateToBack) })
            AddButton(onClick = { viewModel.add(snapshot, onNavigateToBack) })
        }
    }
}

@Composable
private fun photoImage(
    uiState: SpotAddUiState,
    onClickImageRotate: () -> Unit,
): (() -> Bitmap)? {
    var snapshot: (() -> Bitmap)? = null
    val angle: Float by animateFloatAsState(targetValue = uiState.imageAngle, label = "")
    val imageSize = 150.dp
    Timber.d("photoImage::${uiState.image}")

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(roundedMiddle))
            .size(imageSize)
    ) {
        snapshot = captureBitmap {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiState.image.ifEmpty { R.mipmap.photo_placeholder })
                    .allowHardware(false)
                    .build(),
                placeholder = painterResource(id = R.mipmap.photo_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(imageSize)
                    .rotate(angle)
            )
        }
        FilledIconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = onClickImageRotate,
        ) {
            Icon(imageVector = Icons.Rounded.RotateRight, contentDescription = null)
        }
    }

    return snapshot
}

@Composable
private fun SpotName(uiState: SpotAddUiState, onNewValue: (String) -> Unit) {
    SingleLineTextField(
        value = uiState.name,
        onNewValue = onNewValue,
        placeholder = stringResource(id = R.string.spot_name_placeholder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingSmall),
        leadingIcon = null,
    )
}

@Composable
private fun SpotAddress(uiState: SpotAddUiState, onNewValue: (String) -> Unit) {
    SingleLineTextField(
        value = uiState.address,
        onNewValue = onNewValue,
        placeholder = stringResource(id = R.string.spot_address_placeholder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingSmall),
        leadingIcon = null,
    )
}

@Composable
private fun RowScope.SpotLatitude(uiState: SpotAddUiState, onNewValue: (String) -> Unit) {
    SingleLineTextField(
        value = uiState.latitude,
        onNewValue = onNewValue,
        placeholder = stringResource(id = R.string.spot_latitude_placeholder),
        modifier = Modifier
            .weight(1f)
            .padding(paddingSmall),
        leadingIcon = null,
    )
}

@Composable
private fun RowScope.SpotLongitude(uiState: SpotAddUiState, onNewValue: (String) -> Unit) {
    SingleLineTextField(
        value = uiState.longitude,
        onNewValue = onNewValue,
        placeholder = stringResource(id = R.string.spot_longitude_placeholder),
        modifier = Modifier
            .weight(1f)
            .padding(paddingSmall),
        leadingIcon = null,
    )
}

@Composable
private fun SpotUrl(uiState: SpotAddUiState, onNewValue: (String) -> Unit) {
    SingleLineTextField(
        value = uiState.url,
        onNewValue = onNewValue,
        placeholder = stringResource(id = R.string.spot_url_placeholder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingSmall),
        leadingIcon = null,
    )
}

@Composable
private fun SpotTel(uiState: SpotAddUiState, onNewValue: (String) -> Unit) {
    SingleLineTextField(
        value = uiState.tel,
        onNewValue = onNewValue,
        placeholder = stringResource(id = R.string.spot_tel_placeholder),
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingSmall),
        leadingIcon = null,
    )
}

@Composable
private fun SpotMemo(uiState: SpotAddUiState, onNewValue: (String) -> Unit) {
    MultiLineTextField(
        value = uiState.memo,
        onNewValue = onNewValue,
        placeholder = stringResource(id = R.string.spot_memo_placeholder),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(paddingSmall),
        leadingIcon = null,
    )
}

@Composable
private fun AddButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(stringResource(id = R.string.add))
    }
}

@Composable
private fun CancelButton(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(stringResource(id = android.R.string.cancel))
    }
}