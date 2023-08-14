package com.jozu.compose.planfun.presentation.screen.spot

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.jozu.compose.planfun.R
import com.jozu.compose.planfun.presentation.common.composable.SingleLineTextField
import com.jozu.compose.planfun.presentation.theme.paddingSmall

/**
 *
 * Created by jozuko on 2023/08/14.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoSelectBottomSheet(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onEnterUrl: (url: String) -> Unit,
) {
    val modalSheetState = rememberModalBottomSheetState()

    if (visible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = modalSheetState,
            windowInsets = WindowInsets.systemBars,
        ) {
            Column(
                modifier = Modifier
                    .navigationBarsPadding()
                    .fillMaxSize()
            ) {
                UrlInput(onEnterUrl)
                ImagePickerButton(onEnterUrl)
                CameraButton(onDismissRequest, onEnterUrl)
            }
        }
    }
}

@Composable
private fun UrlInput(onEnterUrl: (url: String) -> Unit) {
    var inputValue by remember { mutableStateOf("") }

    ListItem(
        headlineContent = {
            SingleLineTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingSmall),
                value = inputValue,
                label = stringResource(id = R.string.select_image_from_url_title),
                placeholder = stringResource(id = R.string.select_image_from_url_hint),
                onNewValue = { inputValue = it }, leadingIcon = { Icon(Icons.Default.Public, contentDescription = null) },
                keyboardType = KeyboardType.Uri, imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = {
                        onEnterUrl.invoke(inputValue)
                    },
                ),
            )
        },
    )
}

@Composable
private fun ImagePickerButton(onEnterUrl: (url: String) -> Unit) {
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri == null) {
            return@rememberLauncherForActivityResult
        }
        onEnterUrl(uri.toString())
    }

    ListItem(
        headlineContent = {
            TextButton(
                onClick = {
                    // image only
                    //pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    // image and video
                    //pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                    // jpeg only
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType("image/jpeg")))
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(id = R.string.select_image_from_picker))
            }
        },
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CameraButton(onDismissRequest: () -> Unit, onEnterUrl: (url: String) -> Unit, viewModel: PhotoSelectViewModel = hiltViewModel()) {
    var uri: Uri? = null
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            onEnterUrl(uri.toString())
        }
    }

    fun openCamera() {
        uri = viewModel.getImageUri()
        cameraLauncher.launch(uri)
    }

    val permissionState = rememberPermissionState(Manifest.permission.CAMERA) { granted ->
        if (granted) {
            openCamera()
        } else {
            onDismissRequest()
        }
    }

    ListItem(
        headlineContent = {
            TextButton(
                onClick = {
                    if (permissionState.status.isGranted) {
                        openCamera()
                    } else {
                        permissionState.launchPermissionRequest()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(id = R.string.select_image_from_camera))
            }
        },
    )
}

