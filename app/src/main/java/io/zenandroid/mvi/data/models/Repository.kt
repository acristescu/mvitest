package io.zenandroid.mvi.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Repository(
    val id : Long,
    val name : String,
    @Json(name="stargazers_count") val stars : Long
)