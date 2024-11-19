package com.example.miquido.data

import com.example.miquido.data.api.ApiService
import com.example.miquido.data.mapper.toDomain
import com.example.miquido.domain.Photo
import com.example.miquido.domain.repository.PhotoRepository
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    PhotoRepository {

    override suspend fun getPhotos(page: Int, size: Int): Result<List<Photo>> {
        return try {
            val photos = apiService.getPhotos(page)
            Result.success(photos.map { it.toDomain(size) })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPhotoById(id: String): Result<Photo> {
        return try {
            val photo = apiService.getPhotoDetails(id)
            Result.success(photo.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}