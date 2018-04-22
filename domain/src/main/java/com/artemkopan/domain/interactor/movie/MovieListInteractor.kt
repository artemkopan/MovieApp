package com.artemkopan.domain.interactor.movie

import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.domain.utils.UiState
import io.reactivex.Observable

interface MovieListInteractor {

    fun setSort(sort: MovieSort)

    fun observer(): Observable<UiState<List<MovieEntity>>>

    fun loadPage()

    fun nextPage()

    fun dispose()

}
