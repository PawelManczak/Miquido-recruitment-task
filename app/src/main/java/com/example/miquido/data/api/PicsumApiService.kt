package com.example.miquido.data.api

import com.example.miquido.data.model.PhotoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2/list/")
    suspend fun getPhotos(
        @Query("page") page: Int, @Query("limit") limit: Int = 20
    ): Response<List<PhotoDto>>

    @GET("id/{id}/info")
    suspend fun getPhotoDetails(
        @Path("id") id: String
    ): Response<PhotoDto>
}
