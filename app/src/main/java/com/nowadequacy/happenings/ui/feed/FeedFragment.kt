package com.nowadequacy.happenings.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.nowadequacy.happenings.R
import com.nowadequacy.happenings.databinding.FragmentFeedBinding
import com.nowadequacy.happenings.ui.feed.adapters.FeedItemAdapter
import com.nowadequacy.happenings.ui.feed.adapters.FeedLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FeedFragment @Inject constructor(
    var viewModel: FeedViewModel,
    var feedItemAdapter: FeedItemAdapter
) : Fragment(R.layout.fragment_feed) {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFeedBinding.bind(view)
        setupFeedRecyclerview()
        lifecycleScope.launch {
            viewModel.loadCards().observe(viewLifecycleOwner){
            }
        }
    }

    private fun setupFeedRecyclerview() {
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = feedItemAdapter.withLoadStateHeaderAndFooter(
                FeedLoadStateAdapter { feedItemAdapter.retry() },
                FeedLoadStateAdapter { feedItemAdapter.retry() }
            )

            btnRetry.setOnClickListener { feedItemAdapter.retry() }

        }
        feedItemAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                // empty state
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    feedItemAdapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }

            }

        }
        lifecycleScope.launch {
            viewModel.loadCards().observe(viewLifecycleOwner) {
                feedItemAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}