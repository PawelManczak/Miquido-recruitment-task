package com.example.miquido.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miquido.domain.Photo
import com.example.miquido.domain.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListScreenViewModel @Inject constructor(private val repository: PhotoRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(PhotoListScreenState())
    val state = _state.asStateFlow()

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val photos = repository.getPhotos(state.value.currentPage)
            if (photos.isSuccess) {
                _state.update {
                    it.copy(
                        photos = photos.getOrDefault(emptyList()), isLoading = false
                    )
                }
            }

        }
    }
}

data class PhotoListScreenState(
    val photos: List<Photo> = emptyList(),
    val isLoading: Boolean = true,
    val currentPage: Int = 1,
)