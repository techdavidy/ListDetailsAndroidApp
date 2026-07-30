package com.dsv.listdetailsdemoapp.ui.detail

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
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysTitleAndBodyWhenStateIsSuccess() {
        setScreenContent(UiState.Success(POST))

        composeTestRule.onNodeWithText("Jetpack Post").assertIsDisplayed()
        composeTestRule.onNodeWithText("Jetpack Body").assertIsDisplayed()
    }

    @Test
    fun displaysProgressIndicatorWhenStateIsLoading() {
        setScreenContent(UiState.Loading)

        composeTestRule
            .onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun displaysErrorMessageAndRetryButtonWhenStateIsError() {
        setScreenContent(UiState.Error("Something went wrong"))

        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
        composeTestRule.onNodeWithText(RETRY_LABEL).assertIsDisplayed()
    }

    @Test
    fun invokesOnRetryWhenRetryButtonIsClicked() {
        var retryCount = 0
        setScreenContent(UiState.Error("Something went wrong"), onRetry = { retryCount++ })

        composeTestRule.onNodeWithText(RETRY_LABEL).performClick()

        assertEquals(1, retryCount)
    }

    @Test
    fun displaysShareButtonWhenStateIsSuccess() {
        setScreenContent(UiState.Success(POST))

        composeTestRule.onNodeWithText(SHARE_LABEL).assertIsDisplayed()
    }

    @Test
    fun invokesOnShareWhenShareButtonIsClicked() {
        var shareCount = 0
        setScreenContent(UiState.Success(POST), onShare = { shareCount++ })

        composeTestRule.onNodeWithText(SHARE_LABEL).performClick()

        assertEquals(1, shareCount)
    }

    private fun setScreenContent(
        state: UiState<Post>,
        onRetry: () -> Unit = {},
        onShare: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            MaterialTheme {
                DetailScreen(state = state, onRetry = onRetry, onShare = onShare)
            }
        }
    }

    private companion object {
        const val RETRY_LABEL = "Retry"
        const val SHARE_LABEL = "Share"
        val POST = Post(id = 42, title = "Jetpack Post", body = "Jetpack Body")
    }
}
