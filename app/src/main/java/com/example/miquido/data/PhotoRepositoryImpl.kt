package com.example.miquido.data

import com.example.miquido.data.api.ApiService
import com.example.miquido.data.mapper.toDomain
import com.example.miquido.data.networking.safeCall
import com.example.miquido.domain.Photo
import com.example.miquido.domain.repository.PhotoRepository
import com.example.miquido.domain.util.NetworkError
import com.example.miquido.domain.util.Result
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    PhotoRepository {

    override suspend fun getPhotos(
        page: Int, size: Int
    ): Result<List<Photo>, NetworkError> {
        return safeCall {
            val response = apiService.getPhotos(page)

            if (response.isSuccessful) {
                response.body()?.map { it.toDomain() } ?: throw SerializationException()
            } else {
                throw HttpException(response)
            }
        }
    }

    override suspend fun getPhotoById(id: String): Result<Photo, NetworkError> {
        return safeCall {
            val response = apiService.getPhotoDetails(id)

            if (response.isSuccessful) {
                response.body()?.toDomain() ?: throw SerializationException()
            } else {
                throw HttpException(response)
            }
        }
    }
}