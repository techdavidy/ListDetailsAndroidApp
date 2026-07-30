package com.dsv.listdetailsdemoapp.ui.detail

import android.os.Bundle
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.ui.UiState
import com.dsv.listdetailsdemoapp.ui.detail.DetailFragment.Companion.PARAM_POST_ID
import com.dsv.listdetailsdemoapp.util.launchFragmentInHiltContainer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Covers only what is specific to the fragment: reading `postId` from the arguments, triggering the
 * initial load, and routing the retry press back to the [DetailViewModel]. Rendering of each
 * [UiState] branch lives in [DetailScreenTest].
 */
@RunWith(AndroidJUnit4::class)
class DetailFragmentTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Test
    fun loadsPostDetailWithTheArgumentPostIdOnLaunch() {
        // 1. Arrange
        val viewModel = mockDetailViewModel(UiState.Loading)

        // 2. Act
        launchDetailFragment(viewModel)

        // 3. Assert — onViewCreated triggers exactly one load
        composeTestRule.waitForIdle()
        verify(exactly = 1) { viewModel.loadPostDetail(POST_ID) }
    }

    @Test
    fun retryButtonLoadsPostDetailAgainWhenStateIsError() {
        // 1. Arrange
        val viewModel = mockDetailViewModel(UiState.Error("Something went wrong"))
        launchDetailFragment(viewModel)

        // 2. Act — press the button in the error state
        composeTestRule.onNodeWithText(RETRY_LABEL).performClick()

        // 3. Assert — one load from onViewCreated, one from the retry press
        verify(exactly = 2) { viewModel.loadPostDetail(POST_ID) }
    }

    @Test
    fun shareButtonRoutesToTheViewModel() {
        // 1. Arrange
        val post = Post(id = POST_ID, title = "Jetpack Post", body = "Jetpack Body")
        val viewModel = mockDetailViewModel(UiState.Success(post))
        launchDetailFragment(viewModel)

        // 2. Act
        composeTestRule.onNodeWithText(SHARE_LABEL).performClick()

        // 3. Assert
        verify(exactly = 1) { viewModel.onShareClick() }
    }

    private fun mockDetailViewModel(state: UiState<Post>): DetailViewModel =
        mockk<DetailViewModel>(relaxed = true).also { viewModel ->
            every { viewModel.uiState } returns MutableStateFlow(state)
        }

    private fun launchDetailFragment(viewModel: DetailViewModel) {
        val testFactory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
                DetailFragment { _ -> viewModel }
        }

        launchFragmentInHiltContainer<DetailFragment>(
            fragmentArgs = Bundle().apply { putInt(PARAM_POST_ID, POST_ID) },
            factory = testFactory,
        )
    }

    private companion object {
        const val POST_ID = 42
        const val RETRY_LABEL = "Retry"
        const val SHARE_LABEL = "Share"
    }
}
