package com.example.miquido.domain.repository

import com.example.miquido.domain.Photo

interface PhotoRepository {
    suspend fun getPhotos(page: Int, size: Int = 400): Result<List<Photo>>
    suspend fun getPhotoById(id: String): Result<Photo>
}