package com.example.miquido.presentation

import com.example.miquido.domain.Photo


sealed interface PhotoListAction {
    data class OnPhotoClicked(val photo: Photo) : PhotoListAction
    data object OnRetryClicked : PhotoListAction
    data object OnLoadMorePhotos : PhotoListAction

}