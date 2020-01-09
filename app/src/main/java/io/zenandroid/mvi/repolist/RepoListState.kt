package io.zenandroid.mvi.repolist

import io.zenandroid.mvi.data.models.Repository

data class RepoListState(
    val loading: Boolean = false,
    val nextPage: Int = 1, // note: the Github api is 1-based
    val data: List<Repository> = emptyList(),
    val errorMessage: String? = null
)