package com.artemkopan.movie.di.module

import com.artemkopan.domain.interactor.movie.*
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    @MovieTopRated
    fun provideTopRated(interactor: MovieListInteractorImpl): MovieListInteractor {
        interactor.setSort(MovieSort.TOP_RATED)
        return interactor
    }

    @Provides
    @MoviePopular
    fun providePopular(interactor: MovieListInteractorImpl): MovieListInteractor {
        interactor.setSort(MovieSort.POPULAR)
        return interactor
    }
}