package com.example.miquido.presentation.view_model

import org.junit.Assert.*

import com.example.miquido.domain.Photo
import com.example.miquido.domain.repository.PhotoRepository
import com.example.miquido.domain.util.Result
import com.example.miquido.domain.util.NetworkError
import com.example.miquido.presentation.PhotoListAction
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.example.miquido.presentation.view_model.PhotoListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoListViewModelTest {

    private val repository: PhotoRepository = mockk()
    private lateinit var viewModel: PhotoListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
       // viewModel = PhotoListViewModel(repository)
    }

    @Test
    fun `init loads photos successfully`() = runTest {
        // Given
        val mockPhotos = listOf(
            Photo(id = "1", author = "Author 1", width = 100, height = 100, imageUrl = "url1"),
            Photo(id = "2", author = "Author 2", width = 200, height = 200, imageUrl = "url2")
        )
        coEvery { repository.getPhotos(any()) } returns Result.Success(mockPhotos)

        // When
        viewModel = PhotoListViewModel(repository)
       // viewModel.onAction(PhotoListAction.OnLoadMorePhotos) // same as init


        // Advance coroutines to finish jobs
        advanceUntilIdle()

        // Then
        assertEquals(mockPhotos, viewModel.state.value.photos)
        assertEquals(false, viewModel.state.value.isLoading)
        assertEquals(2, viewModel.state.value.currentPage) // Incremented
    }
}
