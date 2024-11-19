package com.example.miquido.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.miquido.domain.Photo
import com.example.miquido.presentation.view_model.PhotoListScreenViewModel

@Composable
fun PhotoListScreen(navController: NavController) {

    val vm = hiltViewModel<PhotoListScreenViewModel>()

    val state = vm.state.collectAsState().value

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(state.photos) { photo ->
                    PhotoListItem(photo) { }
                }
            }
        }

    }
}

@Composable
fun PhotoListItem(photo: Photo, onItemClick: (Photo) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(photo) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = photo.download_url,
            contentDescription = "Photo by ${photo.author}",
            modifier = Modifier
                .size(64.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "ID: ${photo.id}")
    }
}