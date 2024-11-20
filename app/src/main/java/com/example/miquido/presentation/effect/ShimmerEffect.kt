package com.example.miquido.presentation.effect

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miquido.presentation.theme.MiquidoTheme

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.DarkGray.copy(alpha = 0.4f),
        Color.Gray.copy(alpha = 0.3f),
        Color.DarkGray.copy(alpha = 0.4f)
    )

    val transition = rememberInfiniteTransition()
    val xShimmer = transition.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(xShimmer.value, 0f),
        end = Offset(xShimmer.value + 300f, 300f)
    )

    Box(
        modifier = modifier.background(brush)
    )
}

@Composable
@Preview(showBackground = true)
fun ShimmerEffectPreview() {
    MiquidoTheme {
        Box(
            modifier = Modifier.size(200.dp)
        ) {
            ShimmerEffect(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
