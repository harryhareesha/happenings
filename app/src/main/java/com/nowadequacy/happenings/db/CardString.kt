package com.nowadequacy.happenings.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardString (
    @PrimaryKey
    val text: String
        )