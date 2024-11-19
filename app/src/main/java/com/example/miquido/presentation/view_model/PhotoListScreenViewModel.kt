package com.example.miquido.presentation.view_model

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoListScreenViewModel @Inject constructor(): ViewModel() {

    init {
        loadPhotos()
    }

    private fun loadPhotos() {
        TODO("Not yet implemented")
    }
}

data class PhotoListScreenState(
   // val photos: List<Photo> = emptyList()
    val isLoading: Boolean = false,

)