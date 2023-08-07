package com.jozu.compose.planfun.presentation.screen.spot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozu.compose.planfun.domain.spot.SpotFuture
import com.jozu.compose.planfun.usecase.SpotAddUseCase
import com.jozu.compose.planfun.usecase.SpotGetUseCase
import com.jozu.compose.planfun.usecase.SpotWatchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class SpotListViewModel @Inject constructor(
    private val spotGetCase: SpotGetUseCase,
    private val spotWatchCase: SpotWatchUseCase,
    private val spotAddCase: SpotAddUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<SpotListUiState> = MutableStateFlow(SpotListUiState())
    val uiState = _uiState.asStateFlow()

    fun refreshSpotList() {
        viewModelScope.launch {
            val spotListFuture = spotGetCase.getInitialData()
            if (spotListFuture is SpotFuture.Success) {
                _uiState.update { uiState ->
                    uiState.updateSpotList(spotListFuture.value)
                }
            } else {
                // TODO エラー処理
            }

            spotWatchCase.addListener().collect { spotChanges ->
                _uiState.update { uiState ->
                    val newSpotList = spotWatchCase.merge(uiState.spotList, spotChanges)
                    uiState.updateSpotList(newSpotList)
                }
            }
        }
    }

    fun onShowAdd() {
        _uiState.update {
            it.changeAddDialogVisibility(true)
        }
    }

    fun onDismissAdd() {
        _uiState.update {
            it.changeAddDialogVisibility(false)
        }
    }
}