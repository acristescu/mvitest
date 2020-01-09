package io.zenandroid.mvi.data

import io.reactivex.Single
import io.zenandroid.mvi.data.models.SearchResults
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("search/repositories")
    fun search(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): Single<SearchResults>
}