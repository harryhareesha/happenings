package com.nowadequacy.happenings.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardDao {
    @Query("SELECT * FROM cardstring")
    fun getAllCards(): List<CardString>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: CardString)

    @Query("DELETE FROM cardstring")
    suspend fun deleteAllCards()
}
