package com.nowadequacy.happenings.ui.feed

import android.content.Context
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class FeedNavigationHostFragment : NavHostFragment() {
    @Inject lateinit var feedFragmentFactory : FeedFragmentFactory
    override fun onAttach(context: Context) {
        super.onAttach(context)
        childFragmentManager.fragmentFactory = feedFragmentFactory
    }
}