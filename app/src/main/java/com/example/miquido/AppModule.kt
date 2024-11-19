package com.example.miquido

import com.example.miquido.data.PhotoRepositoryImpl
import com.example.miquido.domain.ApiService
import com.example.miquido.domain.repository.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePhotoRepository(apiService: ApiService): PhotoRepository {
        return PhotoRepositoryImpl(apiService = apiService)
    }
}
