package com.example.miquido.presentation.screen

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.example.miquido.R
import com.example.miquido.domain.Photo
import com.example.miquido.presentation.PhotoListScreenState

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PhotoDetailsScreenTest {

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
    fun photoDetailsScreen_showsLoadingIndicator_whenLoading() {
        val state = PhotoListScreenState(
            isLoading = true,
            selectedPhoto = null
        )

        composeTestRule.setContent {
            PhotoDetailsScreen(
                state = state
            )
        }

        composeTestRule.onNode(hasTestTag(resources.getString(R.string.loading_indicator))).assertExists()
    }

    @Test
    fun photoDetailsScreen_showsPhotoDetails_whenPhotoIsSelected() {
        val selectedPhoto = Photo(
            id = "1",
            author = "john",
            width = 1920,
            height = 1080,
            imageUrl = "link"
        )

        val state = PhotoListScreenState(
            isLoading = false,
            selectedPhoto = selectedPhoto
        )

        composeTestRule.setContent {
            PhotoDetailsScreen(
                state = state
            )
        }

        composeTestRule.onNodeWithText(resources.getString(R.string.photo_details_screen)).assertExists()
        composeTestRule.onNodeWithText("john").assertExists()
        composeTestRule.onNodeWithText("1920").assertExists()
        composeTestRule.onNodeWithText("1080").assertExists()
        composeTestRule.onNodeWithText("link").assertExists()
    }

}