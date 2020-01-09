package io.zenandroid.mvi.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResults(
    val items : List<Repository>
)