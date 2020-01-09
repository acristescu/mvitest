package io.zenandroid.mvi.repolist

import android.util.Log
import io.zenandroid.mvi.mvi.Reducer
import io.zenandroid.mvi.repolist.RepoListAction.*

class RepoListReducer : Reducer<RepoListState, RepoListAction> {
    override fun reduce(state: RepoListState, action: RepoListAction): RepoListState  {
        Log.i("****", "reduce $action $state")
        return when (action) {
            LoadNextPage, LoadFirstPage -> state
            StartDataLoading -> state.copy(
                loading = true
            )
            is DataLoaded -> if(state.nextPage == action.pageIndex)
                state.copy (
                    loading = false,
                    data = state.data + action.items,
                    nextPage = state.nextPage + 1
                ) else state
            is DataLoadingError -> state.copy(
                loading = false,
                errorMessage = "${action.e.javaClass.name} ${action.e.message}"
            )
        }
    }
}