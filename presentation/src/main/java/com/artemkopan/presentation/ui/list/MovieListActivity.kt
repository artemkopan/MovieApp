package com.artemkopan.presentation.ui.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.HORIZONTAL
import android.support.v7.widget.RecyclerView
import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.domain.interactor.movie.MovieSort
import com.artemkopan.domain.utils.Logger
import com.artemkopan.domain.utils.UiState
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.BaseActivity
import com.artemkopan.presentation.base.Injectable
import com.artemkopan.presentation.extensions.dimen
import com.artemkopan.presentation.misc.EndlessRecyclerScrollListener
import com.artemkopan.presentation.misc.SpaceItemDecoration
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_movie_list.*
import javax.inject.Inject


class MovieListActivity : BaseActivity<MovieListViewModel>(), Injectable {

    @Inject
    lateinit var topRatedAdapter: MovieListAdapter
    @Inject
    lateinit var popularAdapter: MovieListAdapter

    override fun onCreated(savedInstanceState: Bundle?) {
        initList(MovieSort.POPULAR, popularAdapter, popularRecycler)
        initList(MovieSort.TOP_RATED, topRatedAdapter, topRatedRecycler)

        popularAdapter.clickEvent = { viewId, pos, item ->
            Logger.d("id $viewId pos $pos item $item")
        }
    }

    override fun getContentView(): Int = R.layout.activity_movie_list

    override fun getViewModelClass(): Class<MovieListViewModel> = MovieListViewModel::class.java

    private fun initList(sort: MovieSort, adapter: MovieListAdapter, recyclerView: RecyclerView) {
        setupRecycler(recyclerView, adapter)

        val endlessListener = object : EndlessRecyclerScrollListener(recyclerView.layoutManager) {
            override fun onLoadMore(totalItemsCount: Int) {
                viewModel.nextPage(sort)
            }
        }

        recyclerView.addOnScrollListener(endlessListener)
        viewModel.subscribe(sort, dataConsumer(adapter, endlessListener)).addTo(destroyDisposable)
    }

    private fun setupRecycler(view: RecyclerView, adapter: MovieListAdapter) {
        view.adapter = adapter
        view.layoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        view.addItemDecoration(SpaceItemDecoration(dimen(R.dimen.movie_item_space), true))
    }


    private fun dataConsumer(adapter: MovieListAdapter,
                             endlessListener: EndlessRecyclerScrollListener): Consumer<UiState<List<MovieEntity>>> {
        return Consumer {
            adapter.showFooter = it.isLoading
            endlessListener.isEnable = !it.isLoading
            when {
                it.isError -> {

                }
                it.isSuccess -> {
                    adapter.submitList(it.data)
                }
            }
        }
    }
}