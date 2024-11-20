package com.example.miquido.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miquido.domain.repository.PhotoRepository
import com.example.miquido.domain.util.onError
import com.example.miquido.domain.util.onSuccess
import com.example.miquido.presentation.PhotoListAction
import com.example.miquido.presentation.PhotoListEvent
import com.example.miquido.presentation.PhotoListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(private val repository: PhotoRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(PhotoListScreenState())
    val state = _state.asStateFlow()

    private val _events = Channel<PhotoListEvent>()
    val events = _events.receiveAsFlow()

    private var isLoadingMore = false

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        if (state.value.isLoading || isLoadingMore) return

        isLoadingMore = true
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            repository.getPhotos(state.value.currentPage).onSuccess {
                val newPhotos = it
                _state.update {
                    it.copy(
                        photos = it.photos + newPhotos,
                        isLoading = false,
                        currentPage = it.currentPage + 1
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isLoading = false, error = error) }
                _events.send(PhotoListEvent.Error(error))
            }
            isLoadingMore = false
        }
    }

    fun onAction(action: PhotoListAction) {
        when (action) {
            is PhotoListAction.OnPhotoClicked -> {
                _state.update { it.copy(selectedPhoto = action.photo) }
            }

            is PhotoListAction.OnRetryClicked -> {
                loadPhotos()
            }

            PhotoListAction.OnLoadMorePhotos -> {
                loadPhotos()
            }
        }
    }
}