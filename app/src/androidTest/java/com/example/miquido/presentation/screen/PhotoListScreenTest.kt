package com.example.miquido.presentation.screen

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import com.example.miquido.R
import com.example.miquido.domain.Photo
import com.example.miquido.domain.util.NetworkError
import com.example.miquido.presentation.PhotoListAction
import com.example.miquido.presentation.util.toString
import com.example.miquido.presentation.PhotoListScreenState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class PhotoListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context
    private lateinit var resources: Resources

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        resources = context.resources
    }

    @Test
    fun photoListScreen_showsPhotoList() {
        val photos = listOf(
            Photo(id = "1", author = "", width = 10, height = 10, imageUrl = ""),
            Photo(id = "2", author = "", width = 10, height = 10, imageUrl = ""),
            Photo(id = "3", author = "", width = 10, height = 10, imageUrl = "")
        )
        val state = PhotoListScreenState(photos = photos, isLoading = false, error = null)

        composeTestRule.setContent {
            PhotoListScreen(state = state, onAction = {})
        }

        photos.forEach { photo ->
            composeTestRule.onNodeWithText("id: ${photo.id}").assertExists()
        }
    }

    @Test
    fun photoListScreen_showsLoadingIndicator_whenLoading() {
        val state = PhotoListScreenState(isLoading = true, photos = emptyList(), error = null)

        composeTestRule.setContent {
            PhotoListScreen(state = state, onAction = {})
        }

        composeTestRule.onNode(hasTestTag(resources.getString(R.string.loading_indicator)))
            .assertExists()
    }

    @Test
    fun photoListScreen_showsErrorMessage_whenErrorOccurs() {
        val state = PhotoListScreenState(
            isLoading = false,
            photos = emptyList(),
            error = NetworkError.NO_INTERNET
        )

        composeTestRule.setContent {
            PhotoListScreen(
                state = state,
                onAction = {}
            )
        }

        composeTestRule.onNodeWithText(NetworkError.NO_INTERNET.toString(context)).assertExists()
        composeTestRule.onNodeWithText(resources.getString(R.string.retry)).assertExists()
    }

    @Test
    fun photoListScreen_triggersOnPhotoClickedAction_whenPhotoClicked() {
        val photos = listOf(
            Photo(
                id = "1",
                author = "Author 1",
                width = 100,
                height = 100,
                imageUrl = "https://via.placeholder.com/150"
            )
        )
        val state = PhotoListScreenState(photos = photos, isLoading = false, error = null)
        var clickedPhoto: Photo? = null

        composeTestRule.setContent {
            PhotoListScreen(
                state = state,
                onAction = { action ->
                    if (action is PhotoListAction.OnPhotoClicked) {
                        clickedPhoto = action.photo
                    }
                }
            )
        }

        composeTestRule.onNodeWithTag("1").assertExists()
        composeTestRule.onNodeWithTag("1").performClick()

        assertEquals(photos[0], clickedPhoto)
    }


    @Test
    fun photoListScreen_triggersOnRetryClickedAction_whenRetryButtonClicked() {
        val state = PhotoListScreenState(
            isLoading = false,
            photos = emptyList(),
            error = NetworkError.SERVER_ERROR
        )
        var retryTriggered = false

        composeTestRule.setContent {
            PhotoListScreen(
                state = state,
                onAction = { action ->
                    if (action is PhotoListAction.OnRetryClicked) {
                        retryTriggered = true
                    }
                }
            )
        }

        composeTestRule.onNodeWithText(resources.getString(R.string.retry)).performClick()

        assertTrue(retryTriggered)
    }

}