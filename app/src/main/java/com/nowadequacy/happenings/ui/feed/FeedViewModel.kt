package com.nowadequacy.happenings.ui.feed

import androidx.lifecycle.ViewModel
import com.nowadequacy.happenings.repo.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor (
    private  val feedRepo: FeedRepository): ViewModel() {


    suspend fun loadCards () =  feedRepo.fetchFeed()

}