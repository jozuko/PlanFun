package com.jozu.compose.planfun.presentation.screen.spot

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jozu.compose.planfun.R

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun SpotListScreen(
    viewModel: SpotListViewModel = hiltViewModel(),
    onNavigateToSpotAdd: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.refreshSpotList()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToSpotAdd) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = stringResource(id = R.string.spot_add_spot))
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            SpotList(uiState)
        }
    }
}

@Composable
private fun SpotList(uiState: SpotListUiState) {
    LazyColumn {
        items(uiState.spotList) { spot ->
            Text(spot.id ?: "")
        }
    }
}

