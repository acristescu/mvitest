package io.zenandroid.mvi.repolist.items

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.zenandroid.mvi.R

class LoadingItem : Item(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
    }

    override fun getLayout() = R.layout.item_loading

}
