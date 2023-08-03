package com.jozu.compose.planfun.presentation.screen.spot

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jozu.compose.planfun.usecase.SpotAddCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/02.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class SpotAddViewModel @Inject constructor(
    private val spotAddCase: SpotAddCase,
) : ViewModel() {
    private val _spotId: MutableState<String> = mutableStateOf("")
    val spotId get() = _spotId

    private val _spotImage: MutableState<File?> = mutableStateOf(null)
    val spotImage get() = _spotImage

}