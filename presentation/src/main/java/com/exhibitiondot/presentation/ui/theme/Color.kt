package com.exhibitiondot.presentation.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFF007BFF)
val PrimaryVariant = Color(0xFFE1EDFC)
val PrimaryDisabled = Color(0x0D007BFF)
val White = Color(0xFFFFFFFF)
val Grey100 = Color(0xFFEFEFEF)
val Grey200 = Color(0xFFD1D9D9)
val Grey300 = Color(0xFF9D9D9D)
val Grey700 = Color(0xFF68696A)
val Grey800 = Color(0xFF494A4B)
val Black = Color(0xFF000000)
val Red = Color(0xFFD00036)
val RedVariant = Color(0xFFB31312)
val KakaoYellow = Color(0xFFFEE500)

val LightColorScheme = lightColorScheme(
    primary = Primary,
    primaryContainer = PrimaryVariant,
    inversePrimary = PrimaryDisabled,
    background = White,
    onBackground = Black,
    surface = Grey100,
    onSurface = Grey200,
    surfaceContainer = Grey300,
    surfaceContainerLow = Grey700,
    surfaceContainerHigh = Grey800,
    error = Red,
    onError = RedVariant,
    tertiaryContainer = KakaoYellow,
)