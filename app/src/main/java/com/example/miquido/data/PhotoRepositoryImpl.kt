package com.example.miquido.data

import com.example.miquido.domain.ApiService
import com.example.miquido.domain.Photo
import com.example.miquido.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val apiService: ApiService): PhotoRepository {

    override suspend fun getPhotos(page: Int): Result<List<Photo>> {
        return try {
            val photos = apiService.getPhotos(page)
            Result.success(photos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}