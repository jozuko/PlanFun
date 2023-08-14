package com.jozu.compose.planfun.presentation.common.composable

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
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
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions? = null,
) {
    NormalTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onNewValue = onNewValue,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        keyboardType = keyboardType,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
    )
}

@Composable
fun MultiLineTextField(
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier,
    value: String = "",
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType? = null,
    imeAction: ImeAction? = null,
    keyboardActions: KeyboardActions? = null,
) {
    NormalTextField(
        singleLine = false,
        modifier = modifier,
        value = value,
        label = label,
        onNewValue = onNewValue,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        keyboardType = keyboardType,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
    )
}

@Composable
private fun NormalTextField(
    singleLine: Boolean,
    value: String,
    onNewValue: (String) -> Unit,
    label: String?,
    placeholder: String?,
    modifier: Modifier,
    leadingIcon: @Composable (() -> Unit)?,
    keyboardType: KeyboardType?,
    imeAction: ImeAction?,
    keyboardActions: KeyboardActions?,
) {
    OutlinedTextField(
        singleLine = singleLine,
        modifier = modifier,
        value = value,
        onValueChange = onNewValue,
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType ?: KeyboardType.Text,
            imeAction = imeAction ?: ImeAction.Default,
        ),
        keyboardActions = keyboardActions ?: KeyboardActions.Default,
    )
}
