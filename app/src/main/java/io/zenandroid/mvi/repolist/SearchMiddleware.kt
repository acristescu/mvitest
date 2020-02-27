package io.zenandroid.mvi.repolist

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.schedulers.Schedulers
import io.zenandroid.mvi.data.GithubApi
import io.zenandroid.mvi.data.GithubApiFacade
import io.zenandroid.mvi.mvi.Middleware
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class SearchMiddleware : Middleware<RepoListState, RepoListAction> {
    private val retrofit =
        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.github.com")
            .build()
    private val github = GithubApiFacade(retrofit.create(GithubApi::class.java))

    override fun bind(
        actions: Observable<RepoListAction>,
        state: Observable<RepoListState>
    ): Observable<RepoListAction> {
        val loadFirstPageActions = actions.ofType(RepoListAction.LoadFirstPage::class.java)
            .withLatestFrom(state)
            .filter { (_, state) -> state.data.isEmpty() } // ignore this if we already have some data
        val loadNextPageActions = actions.ofType(RepoListAction.LoadNextPage::class.java)
            .withLatestFrom(state)
        return Observable.merge(loadFirstPageActions, loadNextPageActions)
            .flatMap { (_, state) ->
                Log.i("****", "loading ${state.nextPage}")
                github.loadNextPage(state.nextPage)
                    .subscribeOn(Schedulers.io())
                    .map { RepoListAction.DataLoaded(state.nextPage, it.items) as RepoListAction }
                    .toObservable()
                    .startWith(RepoListAction.StartDataLoading)
                    .onErrorReturn (RepoListAction::DataLoadingError )
            }
    }
}