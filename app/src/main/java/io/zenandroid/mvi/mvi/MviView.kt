package io.zenandroid.mvi.mvi

import io.reactivex.Observable

interface MviView<S, A> {
    val actions: Observable<A>
    fun render(state: S)
}