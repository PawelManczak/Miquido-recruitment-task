package com.example.miquido.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.miquido.R
import com.example.miquido.presentation.item.PhotoListItem
import com.example.miquido.presentation.theme.LocalSpacing
import com.example.miquido.presentation.view_model.PhotoListScreenViewModel
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PhotoListScreen(navController: NavController) {

    val vm = hiltViewModel<PhotoListScreenViewModel>()

    val state by vm.state.collectAsState()
    val spacing = LocalSpacing.current

    val gridState = rememberLazyGridState()

    // Implementacja infinite scrolling
    LaunchedEffect(key1 = gridState) {
        snapshotFlow { gridState.firstVisibleItemIndex + gridState.layoutInfo.visibleItemsInfo.size }
            .distinctUntilChanged()
            .collect { visibleItemCount ->
                val totalItemsCount = gridState.layoutInfo.totalItemsCount
                if (visibleItemCount >= totalItemsCount - 3 && !state.isLoading) {
                    vm.loadPhotos()
                }
            }
    }

    Column(Modifier.padding(spacing.regular)) {
        if (state.isLoading && state.photos.isEmpty()) {
            // Pokaż wskaźnik ładowania tylko przy początkowym ładowaniu danych
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Text(
                text = stringResource(R.string.list_of_photos),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(spacing.regular))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState,
                contentPadding = PaddingValues(spacing.small),
                horizontalArrangement = Arrangement.spacedBy(spacing.small),
                verticalArrangement = Arrangement.spacedBy(spacing.small),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.photos) { photo ->
                    PhotoListItem(
                        modifier = Modifier.size(96.dp),
                        photo = photo
                    )
                }

                if (state.isLoading) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        // Wskaźnik ładowania na dole listy
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(spacing.small),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
