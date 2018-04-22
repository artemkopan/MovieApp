package com.artemkopan.movie.di.module

import com.artemkopan.presentation.ui.list.MovieListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    internal abstract fun bindMovieListActivity(): MovieListActivity

}