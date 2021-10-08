package com.nowadequacy.happenings.repo


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.nowadequacy.happenings.api.FeedApi
import com.nowadequacy.happenings.datasource.CardPagingSource
import com.nowadequacy.happenings.db.FeedDB
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedRepositoryImpl @Inject constructor(private val feedApi: FeedApi,
private val db: FeedDB, private val scope: CoroutineScope
): FeedRepository {
    private val cardDao = db.cardDao()
override suspend fun fetchFeed() =
    Pager(
    config = PagingConfig(
    pageSize = 6,
    maxSize = 30,
    enablePlaceholders = false
    ),
    pagingSourceFactory = { CardPagingSource(feedApi, db, scope) }
    ).liveData

}