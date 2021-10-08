package com.nowadequacy.happenings.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nowadequacy.happenings.response.CardX


class RoomTypeConverters {
    @TypeConverter
    fun fromCardX(cardx: CardX): String {
        return Gson().toJson(cardx)
    }

    @TypeConverter
    fun toCardX(text: String): CardX {
        return Gson().fromJson(text, CardX::class.java)
    }

}
