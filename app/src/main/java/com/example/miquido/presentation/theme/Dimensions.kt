package com.example.miquido.presentation.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val mediumPlus: Dp = 12.dp,
    val regular: Dp = 16.dp,
    val regularPlus: Dp = 24.dp,
    val large: Dp = 32.dp,
    val largePlus: Dp = 48.dp,
    val extraLarge: Dp = 64.dp
)

val LocalSpacing = compositionLocalOf { Dimensions() }
val LocalDimensions = compositionLocalOf { Dimensions() }