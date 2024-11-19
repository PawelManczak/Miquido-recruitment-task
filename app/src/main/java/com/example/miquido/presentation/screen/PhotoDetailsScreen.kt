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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.miquido.R
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

            AsyncImage(
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
                        width = spacing.extraSmall, color = Color.LightGray, shape = shapes.regular
                    )
            )

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