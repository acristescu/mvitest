package io.zenandroid.mvi.repolist.items

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.zenandroid.mvi.R
import io.zenandroid.mvi.data.models.Repository
import kotlinx.android.synthetic.main.item_repo.*

class RepoItem (private val repo: Repository) : Item(repo.id) {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            repoName.text = repo.name
            starCount.text = repo.stars.toString()
        }
    }

    override fun getLayout() = R.layout.item_repo
}