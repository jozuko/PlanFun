package com.jozu.compose.planfun.presentation.screen.signin

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.view.MotionEvent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.updateBounds
import com.jozu.compose.planfun.R
import com.jozu.compose.planfun.infra.log.Logger
import com.jozu.compose.planfun.presentation.theme.robotoFamily

/**
 *
 * Created by jozuko on 2023/07/31.
 * Copyright (c) 2023 Studio Jozu. All rights reserved.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInWithGoogleButton(enabled: Boolean, onClick: () -> Unit) {
    val buttonImage = ContextCompat.getDrawable(LocalContext.current, R.drawable.sign_in_with_google)
    val buttonDisabledImage = ContextCompat.getDrawable(LocalContext.current, R.drawable.sign_in_with_google_disable)
    val buttonPressImage = ContextCompat.getDrawable(LocalContext.current, R.drawable.sign_in_with_google_press)
    val pressedState = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .size(height = 40.dp, width = 220.dp)
            .pointerInteropFilter { motionEvent ->
                when (motionEvent.actionMasked) {
                    MotionEvent.ACTION_DOWN -> pressedState.value = true
                    MotionEvent.ACTION_UP -> pressedState.value = false
                    MotionEvent.ACTION_CANCEL -> pressedState.value = false
                }
                false
            }
            .drawBehind {
                if (!enabled) {
                    buttonDisabledImage?.updateBounds(0, 0, size.width.toInt(), size.height.toInt())
                    buttonDisabledImage?.draw(drawContext.canvas.nativeCanvas)
                } else if (pressedState.value) {
                    buttonPressImage?.updateBounds(0, 0, size.width.toInt(), size.height.toInt())
                    buttonPressImage?.draw(drawContext.canvas.nativeCanvas)
                } else {
                    buttonImage?.updateBounds(0, 0, size.width.toInt(), size.height.toInt())
                    buttonImage?.draw(drawContext.canvas.nativeCanvas)
                }
            }
            .clickable {
                Logger.d("SignInWithGoogle clicked")
                onClick.invoke()
            },
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(54.dp))
            Text(
                stringResource(id = R.string.sign_in_with_google),
                fontFamily = robotoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = TextUnit(14f, TextUnitType.Sp),
                color = getTextColor(enabled),
            )
        }
    }
}

@Composable
private fun getTextColor(enabled: Boolean): Color {
    return if (isSystemInDarkTheme()) {
        if (enabled) {
            Color.White
        } else {
            Color.Gray
        }
    } else {
        if (enabled) {
            Color.Black
        } else {
            Color.Gray
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewSignInWithGoogleButton() {
    Column {
        SignInWithGoogleButton(enabled = true) {}
        SignInWithGoogleButton(enabled = false) {}
    }
}