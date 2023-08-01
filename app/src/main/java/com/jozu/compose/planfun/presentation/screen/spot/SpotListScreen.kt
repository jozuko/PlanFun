package com.jozu.compose.planfun.presentation.screen.spot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel

/**
 *
 * Created by jozuko on 2023/08/01.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun SpotListScreen(
    viewModel: SpotListViewModel = hiltViewModel(),
) {
    val spotListState = viewModel.spotListFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshSpotList()
    }

    Column {
        Button(onClick = { viewModel.add() }) {
            Text("add")
        }

        LazyColumn {
            items(spotListState.value) {
                Text(it.id ?: "")
            }
        }
    }
}