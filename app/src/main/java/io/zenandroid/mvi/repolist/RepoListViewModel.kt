package io.zenandroid.mvi.repolist

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import io.zenandroid.mvi.mvi.MviView
import io.zenandroid.mvi.mvi.Store

class RepoListViewModel (private val store: Store<RepoListState, RepoListAction>): ViewModel() {

    private val wiring = store.wire()
    private var viewBinding: Disposable? = null

    override fun onCleared() {
        wiring.dispose()
    }

    fun bind(view: MviView<RepoListState, RepoListAction>) {
        viewBinding = store.bind(view)
    }

    fun unbind() {
        viewBinding?.dispose()
    }
}
