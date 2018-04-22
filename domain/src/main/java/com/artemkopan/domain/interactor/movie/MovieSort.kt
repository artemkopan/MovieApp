package com.artemkopan.domain.interactor.movie

import javax.inject.Qualifier

enum class MovieSort {

    POPULAR,
    TOP_RATED;

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MoviePopular

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MovieTopRated