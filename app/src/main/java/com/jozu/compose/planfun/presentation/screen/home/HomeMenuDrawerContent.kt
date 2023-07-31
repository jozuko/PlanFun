package com.jozu.compose.planfun.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jozu.compose.planfun.R
import com.jozu.compose.planfun.presentation.theme.paddingMiddle

/**
 *
 * Created by jozuko on 2023/07/31.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun HomeMenuDrawerContent(viewModel: HomeViewModel) {
    ModalDrawerSheet {
        DrawerContentRow(stringResource(id = R.string.sign_out), viewModel::onClickSignOut)
    }
}

@Composable
private fun DrawerContentRow(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .clickable { onClick.invoke() },
    ) {
        Text(
            title,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(paddingMiddle),
        )
    }
}
