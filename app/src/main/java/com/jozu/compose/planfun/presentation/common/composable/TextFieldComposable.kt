package com.jozu.compose.planfun.presentation.common.composable

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

/**
 *
 * Created by jozuko on 2023/08/04.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@Composable
fun SingleLineTextField(
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions? = null,
) {
    NormalTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onNewValue = onNewValue,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions,
    )
}

@Composable
fun MultiLineTextField(
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions? = null,
) {
    NormalTextField(
        singleLine = false,
        modifier = modifier,
        value = value,
        onNewValue = onNewValue,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions,
    )
}

@Composable
private fun NormalTextField(
    singleLine: Boolean,
    value: String,
    onNewValue: (String) -> Unit,
    placeholder: String?,
    modifier: Modifier,
    leadingIcon: @Composable (() -> Unit)?,
    keyboardOptions: KeyboardOptions?,
) {
    OutlinedTextField(
        singleLine = singleLine,
        modifier = modifier,
        value = value,
        onValueChange = onNewValue,
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        keyboardOptions = keyboardOptions ?: KeyboardOptions(keyboardType = KeyboardType.Text),
    )
}
