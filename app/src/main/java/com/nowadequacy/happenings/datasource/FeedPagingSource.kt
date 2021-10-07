package com.nowadequacy.happenings.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nowadequacy.happenings.api.FeedApi
import com.nowadequacy.happenings.response.Card
import retrofit2.HttpException
import java.io.IOException

private const val FEED_STARTING_PAGE_INDEX = 0

class CardPagingSource(
    private val feedApi: FeedApi
) : PagingSource<Int, Card>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Card> {
        val position = params.key ?: FEED_STARTING_PAGE_INDEX

        return try {
            val response = feedApi.fetchFeed()
            val cards = response.page.cards
            LoadResult.Page(
                data = cards,
                prevKey = if (position == FEED_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = null
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Card>): Int? {
        return null
    }
}