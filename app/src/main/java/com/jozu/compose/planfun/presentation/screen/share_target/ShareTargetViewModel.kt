package com.jozu.compose.planfun.presentation.screen.share_target

import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.jozu.compose.planfun.domain.share_target.SharedContent
import com.jozu.compose.planfun.usecase.SharedAnalyzeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/18.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class ShareTargetViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    sharedAnalyzeUseCase: SharedAnalyzeUseCase,
) : ViewModel() {
    val sharedContentState: StateFlow<SharedContent<String>> = savedStateHandle.getStateFlow(NavController.KEY_DEEP_LINK_INTENT, Intent())
        .map { intent: Intent -> sharedAnalyzeUseCase.invoke(intent) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = SharedContent.Proceeding
        )


}