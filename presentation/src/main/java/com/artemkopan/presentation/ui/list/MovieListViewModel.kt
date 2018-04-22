package com.artemkopan.presentation.ui.list

import android.support.v4.util.ArrayMap
import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.domain.interactor.movie.MovieListInteractor
import com.artemkopan.domain.interactor.movie.MoviePopular
import com.artemkopan.domain.interactor.movie.MovieSort
import com.artemkopan.domain.interactor.movie.MovieTopRated
import com.artemkopan.domain.utils.UiState
import com.artemkopan.presentation.base.BaseViewModel
import dagger.Module
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import javax.inject.Inject

@Module
class MovieListViewModel @Inject constructor(
        @MoviePopular private val popularInteractor: MovieListInteractor,
        @MovieTopRated private val topRatedInteractor: MovieListInteractor) : BaseViewModel() {

    private val interactors = ArrayMap<MovieSort, MovieListInteractor>()

    init {
        interactors[MovieSort.POPULAR] = popularInteractor.apply { loadPage() }
        interactors[MovieSort.TOP_RATED] = topRatedInteractor.apply { loadPage() }
    }

    fun subscribe(sort: MovieSort, consumer: Consumer<UiState<List<MovieEntity>>>): Disposable {
        return interactors[sort]!!
                .observer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer)
    }

    fun nextPage(sort: MovieSort) {
        interactors[sort]!!.nextPage()
    }

    override fun onCleared() {
        super.onCleared()
        interactors.values.forEach { it.dispose() }
    }

}