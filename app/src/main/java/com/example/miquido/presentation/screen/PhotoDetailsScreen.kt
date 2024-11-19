package com.example.miquido.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.miquido.R
import com.example.miquido.presentation.effect.ShimmerEffect
import com.example.miquido.presentation.item.ErrorContent
import com.example.miquido.presentation.theme.LocalRoundedCornerShapes
import com.example.miquido.presentation.theme.LocalSpacing
import com.example.miquido.presentation.view_model.PhotoListScreenState

@Composable
fun PhotoDetailsScreen(
    modifier: Modifier = Modifier,
    state: PhotoListScreenState,
) {

    val spacing = LocalSpacing.current
    val shapes = LocalRoundedCornerShapes.current
    val selectedPhoto = state.selectedPhoto

    var isError by remember { mutableStateOf(false) }
    var retryTrigger by remember { mutableStateOf(0) }


    if (state.isLoading) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (selectedPhoto != null) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(spacing.regular)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(stringResource(R.string.photo_details_screen), fontSize = 32.sp)

            Spacer(Modifier.height(spacing.regular))

            key(retryTrigger) {
                SubcomposeAsyncImage(
                    model = selectedPhoto.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .shadow(elevation = spacing.medium, shape = shapes.regular, clip = true)
                        .background(Color.White)
                        .clip(shapes.regular)
                        .border(
                            width = spacing.extraSmall,
                            color = Color.LightGray,
                            shape = shapes.regular
                        ),
                    loading = {
                        ShimmerEffect(modifier = Modifier.fillMaxSize())
                    },
                    error = {
                        ErrorContent(onRetry = { retryTrigger++ })
                    },
                    onSuccess = {
                        isError = false
                    }
                )
            }
            Spacer(Modifier.height(spacing.regular))

            Text(stringResource(R.string.author))
            Text(text = selectedPhoto.author)
            Spacer(modifier = Modifier.height(spacing.regular))

            Text(stringResource(R.string.width))
            Text(text = selectedPhoto.width.toString())
            Spacer(modifier = Modifier.height(spacing.regular))

            Text(stringResource(R.string.height))
            Text(text = selectedPhoto.height.toString())
            Spacer(modifier = Modifier.height(spacing.regular))

            Text(stringResource(R.string.url))
            Text(text = selectedPhoto.imageUrl)
            Spacer(modifier = Modifier.height(spacing.regular))

        }
    }
}