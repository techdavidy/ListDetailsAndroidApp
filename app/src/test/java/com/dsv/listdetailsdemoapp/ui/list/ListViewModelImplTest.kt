package com.dsv.listdetailsdemoapp.ui.list

import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.data.repository.PostRepository
import com.dsv.listdetailsdemoapp.ui.UiState
import com.dsv.listdetailsdemoapp.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelImplTest {

    // Helper Rule to swap Dispatchers.Main with a TestDispatcher
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: PostRepository = mockk()

    @Test
    fun `init calls fetchPosts and updates uiState to Success`() = runTest {
        // 1. Arrange
        val expectedPosts = listOf(Post(id = 1, title = "Test Post", body = "Test Body"))
        coEvery { repository.fetchPosts() } returns expectedPosts

        // 2. Act
        val viewModel = ListViewModelImpl(repository)

        // 3. Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is UiState.Success)
        assertEquals(expectedPosts, (currentState as UiState.Success).data)
    }

    @Test
    fun `init updates uiState to Error when repository throws exception`() = runTest {
        // 1. Arrange
        coEvery { repository.fetchPosts() } throws RuntimeException("Database error")

        // 2. Act
        val viewModel = ListViewModelImpl(repository)

        // 3. Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is UiState.Error)
        assertEquals("Database error", (currentState as UiState.Error).message)
    }
}