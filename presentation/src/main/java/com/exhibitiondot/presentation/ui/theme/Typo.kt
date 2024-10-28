package com.exhibitiondot.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.exhibitiondot.presentation.R

private val Pretendard = FontFamily(
    Font(R.font.pretendard_regular),
    Font(R.font.pretendard_medium),
    Font(R.font.pretendard_semibold),
    Font(R.font.pretendard_bold),
)

val DoTTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 46.sp,
        color = Primary
    ),
    headlineLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp,
        color = Black
    ),
    headlineMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 16.sp,
        color = Black
    ),
    headlineSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        color = Black
    ),
    bodyLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W600,
        fontSize = 18.sp,
        color = Black
    ),
    bodyMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp,
        color = Black
    ),
    bodySmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W600,
        fontSize = 14.sp,
        color = Black
    ),
    labelLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        color = Black
    ),
    labelMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        color = Black
    ),
    labelSmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        color = Black
    ),
    displayLarge = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
        color = Black
    ),
    displayMedium = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = Black
    ),
    displaySmall = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        color = Black
    ),
)