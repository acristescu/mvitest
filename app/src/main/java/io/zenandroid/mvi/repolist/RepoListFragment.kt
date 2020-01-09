package io.zenandroid.mvi.repolist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay2.PublishRelay
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import io.reactivex.Observable

import io.zenandroid.mvi.R
import io.zenandroid.mvi.mvi.MviView
import io.zenandroid.mvi.mvi.Store
import io.zenandroid.mvi.repolist.items.LoadingItem
import io.zenandroid.mvi.repolist.items.RepoItem
import kotlinx.android.synthetic.main.repo_list_fragment.*

class RepoListFragment : Fragment(), MviView<RepoListAction, RepoListState> {
    companion object {
        fun newInstance() = RepoListFragment()
    }

    private lateinit var viewModel: RepoListViewModel
    private val group = object : Section() {
        override fun notifyItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.notifyItemRangeInserted(positionStart, itemCount)
            if(positionStart == 0) {
                recycler.scrollToPosition(0)
            }
        }
    }
    private val loadingItem = LoadingItem()
    private val adapter = GroupAdapter<ViewHolder>().apply {
        add(group)
        add(loadingItem)
    }
    private val actionsRelay = PublishRelay.create<RepoListAction>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repo_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.addOnScrollListener( object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    actionsRelay.accept(RepoListAction.LoadNextPage)
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            viewModelFactory {
                RepoListViewModel(
                    Store(
                        RepoListReducer(),
                        listOf(SearchMiddleware()),
                        RepoListState()
                    )
                )
            }
        ).get(RepoListViewModel::class.java)
    }

    override fun onPause() {
        viewModel.unbind()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        viewModel.bind(this)
        actionsRelay.accept(RepoListAction.LoadFirstPage)
    }

    override val actions: Observable<RepoListAction>
        get() = actionsRelay

    override fun render(state: RepoListState) {
        Log.i("****", "render $state")
        group.update(state.data.map(::RepoItem))
    }

    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }
}
