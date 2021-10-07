package com.nowadequacy.happenings.api

import com.nowadequacy.happenings.response.FeedResponse
import retrofit2.http.GET

interface FeedApi {
    companion object {
        const val BASE_URL_FEED = "https://private-8ce77c-tmobiletest.apiary-mock.com/"
    }
    @GET("test/home")
    suspend fun fetchFeed(): FeedResponse
}