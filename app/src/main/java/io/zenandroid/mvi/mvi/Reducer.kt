package io.zenandroid.mvi.mvi

interface Reducer<S, A> {
    fun reduce(state: S, action: A): S
}