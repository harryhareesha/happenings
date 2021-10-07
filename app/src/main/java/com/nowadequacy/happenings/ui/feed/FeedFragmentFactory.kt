package com.nowadequacy.happenings.ui.feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.nowadequacy.happenings.repo.FeedRepository
import com.nowadequacy.happenings.ui.feed.adapters.FeedItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class FeedFragmentFactory @Inject constructor(
    private val feedItemAdapter: FeedItemAdapter,
    private val feedRepository: FeedRepository
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            FeedFragment::class.java.name -> FeedFragment(FeedViewModel(feedRepository), feedItemAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}