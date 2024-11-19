package com.example.miquido.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.miquido.presentation.item.PhotoListItem
import com.example.miquido.presentation.view_model.PhotoListScreenViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhotoListScreen(navController: NavController) {

    val vm = hiltViewModel<PhotoListScreenViewModel>()

    val state = vm.state.collectAsState().value

    Column(Modifier.padding(16.dp)) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            Text(text = "List of photos", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                state.photos.forEach {
                    PhotoListItem(modifier = Modifier.size(96.dp), photo = it)
                }
            }
        }
    }


}
