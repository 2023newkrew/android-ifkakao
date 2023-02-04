package com.example.ifkakao.domain.model

data class Session(
    val DB_ID: String,
    val date: String,
    val time: String,
    val company: String,
    val title: String,
    val type: String,
    val track: String,
)