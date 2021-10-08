package com.nowadequacy.happenings.ui.feed

import androidx.lifecycle.*
import androidx.paging.PagingData
import com.nowadequacy.happenings.repo.FeedRepository
import com.nowadequacy.happenings.response.Card
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor (
    private  val feedRepo: FeedRepository): ViewModel() {

    suspend fun loadCards () =  feedRepo.fetchFeed()

}