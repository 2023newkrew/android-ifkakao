package com.example.ifkakao.domain.model

import java.io.Serializable

data class Session(
    val DB_ID: String = "DB_ID",
    val title: String = "title",
    val company: String = "company",
    val type: String = "type",
    val track: List<String> = listOf("track"),
    val date: String = "date",
    val videoLink: String = "videoLink",
    val tag: String = "tag",
    val description: String = "Description",
    val presenter: String = "presenter",
    val presenterDescription: String = "presenterDescription",
    val presenterImageUrl: String = "presenterImageUrl",
) : Serializable