package io.zenandroid.mvi.data

/**
 * Simple facade that hides the internals from the outside
 */
class GithubApiFacade (private val githubApi: GithubApi) {

    fun loadNextPage(page: Int) = githubApi.search(
        query = "language:java",
        page = page,
        sort = "stars"
    )
}