package com.artemkopan.movie.di.module

import com.artemkopan.data.repository.AndroidResourceRepository
import com.artemkopan.data.repository.KeyDataRepository
import com.artemkopan.data.repository.movie.MovieDataRepository
import com.artemkopan.domain.repository.KeyRepository
import com.artemkopan.domain.repository.ResourceRepository
import com.artemkopan.domain.repository.movie.MovieRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun movieRepo(movieDataRepository: MovieDataRepository): MovieRepository

    @Binds
    fun keyRepo(keyDataRepository: KeyDataRepository): KeyRepository

    @Binds
    fun resRepo(androidResourceRepository: AndroidResourceRepository): ResourceRepository

}
