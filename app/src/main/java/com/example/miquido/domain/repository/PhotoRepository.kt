package com.example.miquido.domain.repository

import com.example.miquido.domain.Photo
import com.example.miquido.domain.util.NetworkError
import com.example.miquido.domain.util.Result

interface PhotoRepository {
    suspend fun getPhotos(page: Int, size: Int = 400): Result<List<Photo>, NetworkError>
    suspend fun getPhotoById(id: String): Result<Photo, NetworkError>
}