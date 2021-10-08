package com.nowadequacy.happenings.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import com.nowadequacy.happenings.api.FeedApi
import com.nowadequacy.happenings.db.CardString
import com.nowadequacy.happenings.db.FeedDB
import com.nowadequacy.happenings.response.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val FEED_STARTING_PAGE_INDEX = 0

class CardPagingSource(
    private val feedApi: FeedApi, private val db: FeedDB,
    private val scope: CoroutineScope
) : PagingSource<Int, Card>() {

    private val feedDao = db.cardDao()
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Card> {
        val position = params.key ?: FEED_STARTING_PAGE_INDEX

        var cards = listOf<Card>()
        try {
            val response = feedApi.fetchFeed()

            cards = response.page.cards
            scope.launch {
                db.withTransaction {
                   // feedDao.deleteAllCards()
                    feedDao.insertCards(CardString(cards.toString()))
                }
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            //LoadResult.Error(exception)
        }

       scope.launch {
         val abc = feedDao.getAllCards()
           Log.e("abc", "abc=$abc")
           println(abc)
       }
        return LoadResult.Page(
            data = cards,
            prevKey = if (position == FEED_STARTING_PAGE_INDEX) null else position - 1,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Card>): Int? {
        return null
    }

}