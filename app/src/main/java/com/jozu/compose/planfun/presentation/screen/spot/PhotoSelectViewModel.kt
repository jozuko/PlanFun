package com.jozu.compose.planfun.presentation.screen.spot

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.jozu.compose.planfun.usecase.CameraUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/14.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class PhotoSelectViewModel @Inject constructor(
    private val cameraUseCase: CameraUseCase,
) : ViewModel() {
    fun getImageUri(): Uri {
        return cameraUseCase.getImageUri()
    }
}