package com.example.miquido.presentation.item

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.miquido.R
import com.example.miquido.domain.Photo
import com.example.miquido.presentation.theme.LocalRoundedCornerShapes
import com.example.miquido.presentation.theme.LocalSpacing
import com.example.miquido.presentation.theme.MiquidoTheme


@Composable
fun PhotoListItem(
    modifier: Modifier = Modifier, photo: Photo
) {
    val spacing = LocalSpacing.current
    val shapes = LocalRoundedCornerShapes.current

    Box(
        modifier = modifier
            .shadow(elevation = spacing.medium, shape = shapes.regular, clip = true)
            .background(Color.White)
            .clip(shapes.regular)
            .border(
                width = spacing.extraSmall, color = Color.LightGray, shape = shapes.regular
            )
    ) {
        var isImageLoaded by remember { mutableStateOf(false) }

        SubcomposeAsyncImage(model = photo.imageUrl,
            contentDescription = stringResource(R.string.photo_by, photo.author),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            onSuccess = { isImageLoaded = true },
            loading = {
                ShimmerEffect(modifier = Modifier.fillMaxSize())
            },
            error = {
                ErrorContent()
            })
    }
}

@Composable
fun ShimmerEffect(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.Gray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.6f)
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
fun ErrorContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
    }
}

@Composable
@Preview(showBackground = true)
fun PhotoListItemPreview() {
    val photo = Photo(
        id = "1", author = "John Doe", width = 300, height = 200, imageUrl = ""
    )
    MiquidoTheme {
        Box(Modifier.size(200.dp)) {
            PhotoListItem(photo = photo, modifier = Modifier.size(96.dp))

        }
    }
}