package com.jozu.compose.planfun.presentation.screen.spot

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jozu.compose.planfun.domain.Spot
import com.jozu.compose.planfun.domain.SpotFuture
import com.jozu.compose.planfun.usecase.SpotAddCase
import com.jozu.compose.planfun.usecase.SpotGetCase
import com.jozu.compose.planfun.usecase.SpotWatchCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@HiltViewModel
class SpotListViewModel @Inject constructor(
    private val spotGetCase: SpotGetCase,
    private val spotWatchCase: SpotWatchCase,
    private val spotAddCase: SpotAddCase,
) : ViewModel() {
    private val _spotListFlow: MutableStateFlow<List<Spot>> = MutableStateFlow(emptyList())
    val spotListFlow get() = _spotListFlow.asStateFlow()

    private val _isShowAddDialog = mutableStateOf(false)
    val isShowAddDialog: State<Boolean> get() = _isShowAddDialog

    fun refreshSpotList() {
        viewModelScope.launch {
            val spotListFuture = spotGetCase.getInitialData()
            if (spotListFuture is SpotFuture.Success) {
                _spotListFlow.value = spotListFuture.value
            } else {
                // TODO エラー処理
            }

            spotWatchCase.addListener().collect { spotChanges ->
                _spotListFlow.value = spotWatchCase.merge(_spotListFlow.value, spotChanges)
            }
        }
    }

    fun onShowAdd() {
        _isShowAddDialog.value = true
    }

    fun onDismissAdd() {
        _isShowAddDialog.value = false
    }

    fun add() {
        viewModelScope.launch {
            spotAddCase.add()
        }
    }
}