package com.example.miquido.presentation.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.miquido.presentation.PhotoListAction
import com.example.miquido.presentation.PhotoListEvent
import com.example.miquido.presentation.screen.PhotoDetailsScreen
import com.example.miquido.presentation.screen.PhotoListScreen
import com.example.miquido.presentation.util.ObserveAsEvents
import com.example.miquido.presentation.util.toString
import com.example.miquido.presentation.view_model.PhotoListViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun AdaptivePhotoListDetailPane(
    modifier: Modifier = Modifier
) {
    val vm = hiltViewModel<PhotoListViewModel>()
    val state by vm.state.collectAsStateWithLifecycle()


    val context = LocalContext.current
    ObserveAsEvents(events = vm.events) { event ->
        when (event) {
            is PhotoListEvent.Error -> {
                Toast.makeText(context, event.error.toString(context), Toast.LENGTH_LONG).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(navigator = navigator, listPane = {
        AnimatedPane {
            PhotoListScreen(modifier = modifier, state = state, onAction = {
                vm.onAction(it)
                when (it) {
                    is PhotoListAction.OnPhotoClicked -> navigator.navigateTo(
                        ListDetailPaneScaffoldRole.Detail
                    )

                    else -> {}
                }
            })
        }
    }, detailPane = {
        AnimatedPane {
            PhotoDetailsScreen(state = state)
        }
    }, modifier = modifier
    )
}
