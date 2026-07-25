package com.dsv.listdetailsdemoapp.ui.list

import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeListViewModel(
    initialState: UiState<List<Post>> = UiState.Loading
) : ListViewModel {

    override val uiState: StateFlow<UiState<List<Post>>> = MutableStateFlow(initialState)
}