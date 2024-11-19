package com.example.miquido.presentation.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.miquido.R
import com.example.miquido.domain.Photo
import com.example.miquido.presentation.ui.theme.MiquidoTheme

@Composable
fun PhotoListItem(modifier: Modifier = Modifier, photo: Photo) {
        AsyncImage(
            model = photo.imageUrl,
            contentDescription = stringResource(R.string.photo_by, photo.author),
            modifier = modifier,
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

@Composable
@Preview(showBackground = true)
fun PhotoListItemPreview() {
    val photo = Photo(
        id = "1",
        author = "John Doe",
        width = 300,
        height = 200,
        imageUrl = ""
    )
    MiquidoTheme {
        Box(Modifier.size(200.dp)){
            PhotoListItem(photo = photo)

        }
    }
}