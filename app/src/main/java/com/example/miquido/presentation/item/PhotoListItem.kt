package com.example.miquido.presentation.item

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.miquido.R
import com.example.miquido.domain.Photo
import com.example.miquido.presentation.ui.theme.LocalRoundedCornerShapes
import com.example.miquido.presentation.ui.theme.LocalSpacing
import com.example.miquido.presentation.ui.theme.MiquidoTheme

@Composable
fun PhotoListItem(
    modifier: Modifier = Modifier, photo: Photo
) {
    val spacing = LocalSpacing.current
    val shapes = LocalRoundedCornerShapes.current

    Box(
        modifier = modifier
            .shadow(elevation = spacing.medium, shape = shapes.regular, clip = true)
            .clip(shapes.regular)
            .border(
                width = spacing.extraSmall, color = Color.LightGray, shape = shapes.regular
            )
    ) {
        AsyncImage(
            model = photo.imageUrl,
            contentDescription = stringResource(R.string.photo_by, photo.author),
            modifier = Modifier.fillMaxSize(),
            placeholder = BrushPainter(
                Brush.linearGradient(
                    listOf(
                        Color(color = 0xFFFFFFFF),
                        Color(color = 0xFFDDDDDD),
                    )
                )
            ),
        )
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
            PhotoListItem(photo = photo)

        }
    }
}