package com.nowadequacy.happenings.repo


import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.nowadequacy.happenings.response.Card


interface FeedRepository{
suspend fun fetchFeed() : LiveData<PagingData<Card>>
}