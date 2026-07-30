package com.dsv.listdetailsdemoapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment(
    private val viewModelProvider: (Fragment) -> DetailViewModel,
) : Fragment() {
    private val viewModel: DetailViewModel by lazy {
        viewModelProvider(this)
    }

    private val postId: Int by lazy {
        requireArguments().getInt(PARAM_POST_ID)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadPostDetail(postId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    val state = viewModel.uiState.collectAsStateWithLifecycle().value
                    DetailScreen(
                        state = state,
                        onRetry = { viewModel.loadPostDetail(postId) },
                        onShare = { viewModel.onShareClick() },
                    )
                }
            }
        }

    companion object {
        const val PARAM_POST_ID = "postId"
    }
}
