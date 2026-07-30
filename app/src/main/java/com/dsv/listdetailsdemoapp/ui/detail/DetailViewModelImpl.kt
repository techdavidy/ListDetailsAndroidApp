package com.dsv.listdetailsdemoapp.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsv.listdetailsdemoapp.R
import com.dsv.listdetailsdemoapp.data.model.Post
import com.dsv.listdetailsdemoapp.data.repository.PostRepository
import com.dsv.listdetailsdemoapp.ui.UiState
import com.dsv.listdetailsdemoapp.util.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModelImpl
    @Inject
    constructor(
        private val repository: PostRepository,
        private val stringProvider: StringProvider,
    ) : ViewModel(), DetailViewModel {
        private val _uiState = MutableStateFlow<UiState<Post>>(UiState.Loading)
        override val uiState: StateFlow<UiState<Post>> = _uiState

        override fun onShareClick() {
            val post = (uiState.value as? UiState.Success)?.data
            Log.d(TAG, "Share requested: postId=${post?.id}, title=${post?.title}")
        }

        override fun loadPostDetail(postId: Int) {
            viewModelScope.launch {
                _uiState.value = UiState.Loading
                try {
                    val post = repository.fetchPostDetail(postId)
                    _uiState.value = UiState.Success(post)
                } catch (e: Exception) {
                    _uiState.value =
                        UiState.Error(
                            e.localizedMessage ?: stringProvider.getString(R.string.default_server_error),
                        )
                }
            }
        }

        private companion object {
            const val TAG = "DetailViewModel"
        }
    }
