package com.dsv.listdetailsdemoapp.ui.list

import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.ui.UiState
import kotlinx.coroutines.flow.StateFlow

interface ListViewModel {
    val uiState: StateFlow<UiState<List<Post>>>
}