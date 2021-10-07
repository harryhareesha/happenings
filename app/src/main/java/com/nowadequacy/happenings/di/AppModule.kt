package com.nowadequacy.happenings.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.nowadequacy.happenings.R
import com.nowadequacy.happenings.api.FeedApi
import com.nowadequacy.happenings.api.FeedApi.Companion.BASE_URL_FEED
import com.nowadequacy.happenings.repo.FeedRepository
import com.nowadequacy.happenings.repo.FeedRepositoryImpl
import com.nowadequacy.happenings.ui.feed.adapters.FeedItemAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext applicationContext: ApplicationContext) =
        applicationContext


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_FEED)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    fun provideShoppingItemAdapter() = FeedItemAdapter(null)

    @Provides
    fun provideFeedApi(retrofit: Retrofit): FeedApi = retrofit.create(FeedApi::class.java)

    @Provides
    fun provideFeedRepository(feedApi: FeedApi) : FeedRepository = FeedRepositoryImpl(feedApi)
}
