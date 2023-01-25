package com.example.ifkakao.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Timezone(
    val datetime: String,
    val timezone: String,
    val unixtime: Int,
    val utc_datetime: String,
    val utc_offset: String,
)