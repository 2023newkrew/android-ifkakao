package com.example.ifkakao.domain.model

data class Session(
    val DB_ID: String = "DB_ID",
    val title: String = "title",
    val company: String = "company",
    val type: String = "type",
    val date: String = "date",
    val videoLink: String = "videoLink",
    val track: List<String> = listOf("track"),
    val Description: String = "Description",
    val presenter: String = "presenter",
    val presenterDescription: String = "presenterDescription",
    val presenterImageUrl: String = "presenterImageUrl",
)