package com.nowadequacy.happenings.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleRegistry
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.nowadequacy.happenings.R
import com.nowadequacy.happenings.api.FeedApi
import com.nowadequacy.happenings.api.FeedApi.Companion.BASE_URL_FEED
import com.nowadequacy.happenings.db.FeedDB
import com.nowadequacy.happenings.repo.FeedRepository
import com.nowadequacy.happenings.repo.FeedRepositoryImpl
import com.nowadequacy.happenings.ui.feed.adapters.FeedItemAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
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
    @Singleton
    fun provideDatabase(app: Application) : FeedDB =
        Room.databaseBuilder(app, FeedDB::class.java, "feed_database")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun provideDBScope() : CoroutineScope = CoroutineScope(SupervisorJob())

    @Provides
    fun provideShoppingItemAdapter() = FeedItemAdapter(null)

    @Provides
    fun provideFeedApi(retrofit: Retrofit): FeedApi = retrofit.create(FeedApi::class.java)

    @Provides
    fun provideFeedRepository(feedApi: FeedApi, feedDB: FeedDB, scope: CoroutineScope) : FeedRepository =
        FeedRepositoryImpl(feedApi, feedDB, scope)
}
