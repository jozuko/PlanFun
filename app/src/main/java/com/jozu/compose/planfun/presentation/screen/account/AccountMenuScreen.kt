package com.jozu.compose.planfun.presentation.screen.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 *
 * Created by jozuko on 2023/08/09.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun AccountMenuScreen() {
    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Text("AccountMenuScreen")
        }
    }
}