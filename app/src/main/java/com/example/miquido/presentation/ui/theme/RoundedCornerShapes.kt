package com.example.miquido.presentation.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp

data class RoundedCornerShapes(
    val default: RoundedCornerShape = RoundedCornerShape(0.dp),
    val one: RoundedCornerShape = RoundedCornerShape(1.dp),
    val extraSmall: RoundedCornerShape = RoundedCornerShape(2.dp),
    val small: RoundedCornerShape = RoundedCornerShape(4.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(8.dp),
    val regular: RoundedCornerShape = RoundedCornerShape(12.dp),
    val large: RoundedCornerShape = RoundedCornerShape(16.dp),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(24.dp)
)

val LocalRoundedCornerShapes = compositionLocalOf { RoundedCornerShapes() }