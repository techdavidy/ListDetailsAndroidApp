package com.dsv.listdetailsdemoapp.ui.detail

import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.ui.UiState
import kotlinx.coroutines.flow.StateFlow

interface DetailViewModel {
    val uiState: StateFlow<UiState<Post>>

    fun loadPostDetail(postId: Int)
}
