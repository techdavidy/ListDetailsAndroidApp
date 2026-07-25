package com.dsv.listdetailsdemoapp.ui.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dsv.listdetailsdemoapp.R
import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.ui.UiState
import com.dsv.listdetailsdemoapp.util.launchFragmentInHiltContainer
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListFragmentTest {

    @get:Rule
    val composeTestRule = createEmptyComposeRule()

    @Test
    fun displaysPostsInComposeViewWhenStateIsSuccess() {
        // 1. Prepare Fake Data & ViewModel
        val fakePosts = listOf(
            Post(id = 1, title = "First Jetpack Post", body = "First Jetpack Body"),
            Post(id = 2, title = "Second Jetpack Post", body = "Second Jetpack Body")
        )
        val fakeViewModel = FakeListViewModel(
            initialState = UiState.Success(fakePosts)
        )

        // 2. Create Test FragmentFactory
        val testFactory = object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return ListFragment { _ -> fakeViewModel }
            }
        }

        // 3. Launch Fragment using launchFragmentInHiltContainer!
        launchFragmentInHiltContainer<ListFragment>(
            factory = testFactory
        )

        // 4. Assert UI
        composeTestRule
            .onNodeWithText("First Jetpack Post")
            .assertIsDisplayed()
    }
}