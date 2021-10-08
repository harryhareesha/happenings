package com.nowadequacy.happenings.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nowadequacy.happenings.response.Card
@TypeConverters(RoomTypeConverters::class)
@Database(entities = [Card::class, CardString::class], version = 2)
abstract class FeedDB : RoomDatabase() {
    abstract fun cardDao() : CardDao
}