package com.dsv.listdetailsdemoapp.ui.list

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.ui.UiState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysPostTitlesWhenStateIsSuccess() {
        setScreenContent(UiState.Success(POSTS))

        composeTestRule.onNodeWithText("First Jetpack Post").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second Jetpack Post").assertIsDisplayed()
    }

    @Test
    fun displaysProgressIndicatorWhenStateIsLoading() {
        setScreenContent(UiState.Loading)

        composeTestRule
            .onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun displaysErrorMessageWhenStateIsError() {
        setScreenContent(UiState.Error("Something went wrong"))

        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }

    @Test
    fun invokesOnPostClickWithPostIdWhenCardIsClicked() {
        val clickedPostIds = mutableListOf<Int>()
        setScreenContent(UiState.Success(POSTS), onPostClick = clickedPostIds::add)

        composeTestRule.onNodeWithText("Second Jetpack Post").performClick()

        assertEquals(listOf(2), clickedPostIds)
    }

    private fun setScreenContent(
        state: UiState<List<Post>>,
        onPostClick: (Int) -> Unit = {},
    ) {
        composeTestRule.setContent {
            MaterialTheme {
                ListScreen(state = state, onPostClick = onPostClick)
            }
        }
    }

    private companion object {
        val POSTS = listOf(
            Post(id = 1, title = "First Jetpack Post", body = "First Jetpack Body"),
            Post(id = 2, title = "Second Jetpack Post", body = "Second Jetpack Body"),
        )
    }
}
