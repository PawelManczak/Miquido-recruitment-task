package com.example.miquido.data.api

import com.example.miquido.data.model.PhotoDto
import com.example.miquido.domain.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/list/")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20
    ): List<PhotoDto>


}
