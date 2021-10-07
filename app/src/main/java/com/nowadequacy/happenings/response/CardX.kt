package com.nowadequacy.happenings.response

data class CardX(
    val attributes: Attributes,
    val description: Description,
    val image: Image?,
    val title: Title,
    val value: String
)