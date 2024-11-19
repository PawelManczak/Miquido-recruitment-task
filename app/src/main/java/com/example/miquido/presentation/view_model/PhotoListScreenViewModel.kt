package com.example.miquido.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miquido.domain.Photo
import com.example.miquido.domain.repository.PhotoRepository
import com.example.miquido.presentation.PhotoListEvent
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
class PhotoListScreenViewModel @Inject constructor(private val repository: PhotoRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(PhotoListScreenState())
    val state = _state.asStateFlow()

    private val _events = Channel<PhotoListEvent>()
    val events = _events.receiveAsFlow()

    private var isLoadingMore = false

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        if (state.value.isLoading || isLoadingMore) return
        isLoadingMore = true
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val photosResult = repository.getPhotos(state.value.currentPage)
            if (photosResult.isSuccess) {
                val newPhotos = photosResult.getOrDefault(emptyList())
                _state.update {
                    it.copy(
                        photos = it.photos + newPhotos,
                        isLoading = false,
                        currentPage = it.currentPage + 1
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = photosResult.exceptionOrNull()?.message
                    )
                }
            }
            isLoadingMore = false
        }
    }
}

data class PhotoListScreenState(
    val photos: List<Photo> = emptyList(),
    val isLoading: Boolean = false,
    val currentPage: Int = 1,
    val error: String? = null
)
