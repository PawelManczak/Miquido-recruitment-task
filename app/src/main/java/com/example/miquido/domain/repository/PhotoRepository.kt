package com.example.miquido.domain.repository

import com.example.miquido.domain.Photo

interface PhotoRepository {
    suspend fun getPhotos(page: Int): Result<List<Photo>>
}