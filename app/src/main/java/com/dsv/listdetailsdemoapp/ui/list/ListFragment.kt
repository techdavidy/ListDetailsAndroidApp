package com.dsv.listdetailsdemoapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.dsv.listdetailsdemoapp.R
import com.dsv.listdetailsdemoapp.ui.detail.DetailFragment.Companion.PARAM_POST_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment(
    private val viewModelProvider: (Fragment) -> ListViewModel
) : Fragment() {

    private val viewModel: ListViewModel by lazy {
        viewModelProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            MaterialTheme {
                val state = viewModel.uiState.collectAsStateWithLifecycle().value
                ListScreen(
                    state = state,
                    onPostClick = { postId ->
                        navigateToPostDetails(postId)
                    },
                )
            }
        }
    }

    private fun navigateToPostDetails(postId: Int) {
        val bundle = Bundle().apply { putInt(PARAM_POST_ID, postId) }
        findNavController().navigate(R.id.action_listFragment_to_detailFragment, bundle)
    }
}
