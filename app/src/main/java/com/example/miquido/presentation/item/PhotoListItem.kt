package com.example.miquido.presentation.item


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.miquido.R
import com.example.miquido.domain.Photo
import com.example.miquido.presentation.effect.ShimmerEffect
import com.example.miquido.presentation.theme.LocalRoundedCornerShapes
import com.example.miquido.presentation.theme.LocalSpacing
import com.example.miquido.presentation.theme.MiquidoTheme

@Composable
fun PhotoListItem(
    modifier: Modifier = Modifier,
    photo: Photo
) {
    val spacing = LocalSpacing.current
    val shapes = LocalRoundedCornerShapes.current
    var isError by remember { mutableStateOf(false) }
    var retryTrigger by remember { mutableStateOf(0) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        key(retryTrigger) {
            SubcomposeAsyncImage(
                model = photo.imageUrl,
                contentDescription = stringResource(R.string.photo_by, photo.author),
                modifier = Modifier
                    .shadow(
                        elevation = spacing.medium,
                        shape = shapes.regular,
                        clip = true
                    )
                    .background(Color.White)
                    .clip(shapes.regular)
                    .border(
                        width = spacing.extraSmall, color = Color.LightGray, shape = shapes.regular
                    ),
                contentScale = ContentScale.Crop,
                onSuccess = {
                    isError = false
                },
                loading = {
                    ShimmerEffect(modifier = Modifier.fillMaxSize())
                },
                error = {
                    ErrorContent(onRetry = { retryTrigger++ })
                },
                onError = {
                    isError = true
                }
            )
        }
        Text(stringResource(R.string.id, photo.id))
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