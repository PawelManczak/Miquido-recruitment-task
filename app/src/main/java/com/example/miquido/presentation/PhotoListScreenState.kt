package com.example.miquido.presentation

import com.example.miquido.domain.Photo
import com.example.miquido.domain.util.NetworkError

data class PhotoListScreenState(
    val photos: List<Photo> = emptyList(),
    val isLoading: Boolean = false,
    val currentPage: Int = 1,
    val error: NetworkError? = null,
    val selectedPhoto: Photo? = null
)