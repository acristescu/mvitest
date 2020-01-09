package io.zenandroid.mvi.repolist

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.Disposable
import io.zenandroid.mvi.mvi.MviView
import io.zenandroid.mvi.mvi.Store

class RepoListViewModel (private val store: Store<RepoListAction, RepoListState>): ViewModel() {

    private val wiring = store.wire()
    private var viewBinding: Disposable? = null

    override fun onCleared() {
        wiring.dispose()
    }

    fun bind(view: MviView<RepoListAction, RepoListState>) {
        viewBinding = store.bind(view)
    }

    fun unbind() {
        viewBinding?.dispose()
    }
}
