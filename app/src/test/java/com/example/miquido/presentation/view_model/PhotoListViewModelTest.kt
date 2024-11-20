package com.example.miquido.presentation.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.miquido.domain.Photo
import com.example.miquido.domain.repository.PhotoRepository
import com.example.miquido.domain.util.NetworkError
import com.example.miquido.domain.util.Result
import com.example.miquido.presentation.PhotoListAction
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class PhotoListScreenViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private val repository: PhotoRepository = mockk()
    private lateinit var viewModel: PhotoListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = PhotoListViewModel(repository)
    }


    @Test
    fun `init loads photos successfully`() {
        val mockPhotos = listOf(
            Photo(id = "1", author = "Author 1", width = 100, height = 100, imageUrl = "url1"),
            Photo(id = "2", author = "Author 2", width = 200, height = 200, imageUrl = "url2")
        )
        coEvery { repository.getPhotos(1) } returns Result.Success(mockPhotos)


        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(mockPhotos, state.photos)
        assertEquals(false, state.isLoading)
        assertEquals(2, state.currentPage)
        assertEquals(null, state.error)
    }

    @Test
    fun `init handles error when loading photos`() {
        coEvery { repository.getPhotos(1) } returns Result.Error(NetworkError.NO_INTERNET)

        viewModel = PhotoListViewModel(repository)
        dispatcher.scheduler.advanceUntilIdle()


        val state = viewModel.state.value
        assertEquals(emptyList<Photo>(), state.photos)
        assertEquals(false, state.isLoading)
        assertEquals(1, state.currentPage) // Not incremented
        assertEquals(NetworkError.NO_INTERNET, state.error)
    }

    @Test
    fun `onAction OnPhotoClicked updates selectedPhoto`() {
        val photo =
            Photo(id = "1", author = "Author 1", width = 100, height = 100, imageUrl = "url1")

        viewModel.onAction(PhotoListAction.OnPhotoClicked(photo))


        val state = viewModel.state.value
        assertEquals(photo.id, state.selectedPhoto!!.id)
    }

    @Test
    fun `onAction OnRetryClicked triggers loadPhotos`() {
        val mockPhotos = listOf(
            Photo(id = "1", author = "Author 1", width = 100, height = 100, imageUrl = "url1")
        )
        coEvery { repository.getPhotos(1) } returns Result.Success(mockPhotos)
        coEvery { repository.getPhotos(2) } returns Result.Success(emptyList())

        viewModel.onAction(PhotoListAction.OnRetryClicked)
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(mockPhotos, state.photos)
        assertEquals(false, state.isLoading)
        assertEquals(2, state.currentPage)
        assertEquals(null, state.error)
    }

    @Test
    fun `onAction OnLoadMorePhotos loads more photos`() {
        val initialPhotos = listOf(
            Photo(id = "1", author = "Author 1", width = 100, height = 100, imageUrl = "url1")
        )
        val additionalPhotos = listOf(
            Photo(id = "2", author = "Author 2", width = 200, height = 200, imageUrl = "url2")
        )
        coEvery { repository.getPhotos(1) } returns Result.Success(initialPhotos)
        coEvery { repository.getPhotos(2) } returns Result.Success(additionalPhotos)

        viewModel = PhotoListViewModel(repository)
        viewModel.onAction(PhotoListAction.OnLoadMorePhotos)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(initialPhotos, viewModel.state.value.photos)
        assertEquals(2, viewModel.state.value.currentPage)

        viewModel.onAction(PhotoListAction.OnLoadMorePhotos)
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        val expectedPhotos = initialPhotos + additionalPhotos
        assertEquals(expectedPhotos, state.photos)
        assertEquals(false, state.isLoading)
        assertEquals(3, state.currentPage) // Incremented after both loads
        assertEquals(null, state.error)
    }

    @Test
    fun `loadPhotos resets error on new load`() {
        val mockPhotos = listOf(
            Photo(id = "1", author = "Author 1", width = 100, height = 100, imageUrl = "url1")
        )

        var callCount = 0

        coEvery { repository.getPhotos(1) } answers {
            callCount++
            if (callCount == 1) {
                Result.Error(NetworkError.NO_INTERNET)
            } else {
                Result.Success(mockPhotos)
            }
        }

        viewModel = PhotoListViewModel(repository)
        dispatcher.scheduler.advanceUntilIdle()

        viewModel.onAction(PhotoListAction.OnRetryClicked)
        dispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertNull(state.error)
        assertEquals(mockPhotos, state.photos)
    }

}

