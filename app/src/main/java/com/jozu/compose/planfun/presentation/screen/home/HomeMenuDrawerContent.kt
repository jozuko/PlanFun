package com.jozu.compose.planfun.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.jozu.compose.planfun.R
import com.jozu.compose.planfun.presentation.common.AppIcon
import com.jozu.compose.planfun.presentation.theme.paddingMiddle
import kotlinx.coroutines.launch

/**
 *
 * Created by jozuko on 2023/07/31.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun HomeMenuDrawerContent(drawerState: DrawerState, viewModel: HomeViewModel = hiltViewModel()) {
    ModalDrawerSheet {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            AppIcon()
        }
        Divider(modifier = Modifier.padding(vertical = paddingMiddle))
        DrawerContentRow(drawerState, stringResource(id = R.string.drawer_menu_plan), viewModel::onClickMenuPlan)
        DrawerContentRow(drawerState, stringResource(id = R.string.drawer_menu_spot), viewModel::onClickMenuSpot)

        Divider(modifier = Modifier.padding(vertical = paddingMiddle))
        DrawerContentRow(drawerState, stringResource(id = R.string.drawer_menu_sign_out), viewModel::onClickSignOut)
    }
}

@Composable
private fun DrawerContentRow(drawerState: DrawerState, title: String, onClick: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            coroutineScope.launch {
                drawerState.close()
            }
            onClick.invoke()
        },
        colors = ButtonDefaults.textButtonColors(),
    ) {
        Text(
            title,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}
