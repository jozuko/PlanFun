package com.jozu.compose.planfun.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.jozu.compose.planfun.R

// Set of Material typography styles to start with
val robotoFamily = FontFamily(
    Font(R.font.roboto_black, FontWeight.Black),
    Font(R.font.roboto_black_italic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.roboto_bold, FontWeight.Bold),
    Font(R.font.roboto_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.roboto_light, FontWeight.Light),
    Font(R.font.roboto_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.roboto_thin, FontWeight.Thin),
    Font(R.font.roboto_thin_italic, FontWeight.Thin, FontStyle.Italic),
)

val robotoTypography = Typography().let {
    it.copy(
        displayLarge = it.displayLarge.copy(fontFamily = robotoFamily),
        displayMedium = it.displayMedium.copy(fontFamily = robotoFamily),
        displaySmall = it.displaySmall.copy(fontFamily = robotoFamily),
        headlineLarge = it.headlineLarge.copy(fontFamily = robotoFamily),
        headlineMedium = it.headlineMedium.copy(fontFamily = robotoFamily),
        headlineSmall = it.headlineSmall.copy(fontFamily = robotoFamily),
        titleLarge = it.titleLarge.copy(fontFamily = robotoFamily),
        titleMedium = it.titleMedium.copy(fontFamily = robotoFamily),
        titleSmall = it.titleSmall.copy(fontFamily = robotoFamily),
        bodyLarge = it.bodyLarge.copy(fontFamily = robotoFamily),
        bodyMedium = it.bodyMedium.copy(fontFamily = robotoFamily),
        bodySmall = it.bodySmall.copy(fontFamily = robotoFamily),
        labelLarge = it.labelLarge.copy(fontFamily = robotoFamily),
        labelMedium = it.labelMedium.copy(fontFamily = robotoFamily),
        labelSmall = it.labelSmall.copy(fontFamily = robotoFamily),
    )
}
