package com.dsv.listdetailsdemoapp.ui.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.ui.UiState
import com.dsv.listdetailsdemoapp.util.launchFragmentInHiltContainer
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Covers only what is specific to the fragment: that the injected [ListViewModel]'s state is
 * collected and handed to [ListScreen]. Rendering of each [UiState] branch lives in
 * [ListScreenTest].
 */
@RunWith(AndroidJUnit4::class)
class ListFragmentTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Test
    fun rendersStateFromTheInjectedViewModel() {
        // 1. Arrange — strict mock: an unstubbed member would fail loudly
        val posts = listOf(Post(id = 1, title = "First Jetpack Post", body = "First Jetpack Body"))
        val viewModel = mockk<ListViewModel> {
            every { uiState } returns MutableStateFlow(UiState.Success(posts))
        }

        // 2. Act
        launchListFragment(viewModel)

        // 3. Assert
        composeTestRule.onNodeWithText("First Jetpack Post").assertIsDisplayed()
    }

    private fun launchListFragment(viewModel: ListViewModel) {
        val testFactory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment =
                ListFragment { _ -> viewModel }
        }

        launchFragmentInHiltContainer<ListFragment>(factory = testFactory)
    }
}
