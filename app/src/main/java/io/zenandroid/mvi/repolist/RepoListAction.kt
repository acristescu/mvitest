package io.zenandroid.mvi.repolist

import io.zenandroid.mvi.data.models.Repository

sealed class RepoListAction {
    object LoadNextPage: RepoListAction()
    object LoadFirstPage: RepoListAction()
    object StartDataLoading: RepoListAction()

    class DataLoadingError(
        val e: Throwable
    ): RepoListAction()

    class DataLoaded(
        val pageIndex: Int,
        val items: List<Repository>
    ): RepoListAction()
}