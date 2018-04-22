package com.artemkopan.domain.repository.movie

import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.domain.entity.PaginationEntity
import io.reactivex.Single

interface MovieRepository {

    fun getTopRated(apiKey: String, page: Int, language: String): Single<PaginationEntity<MovieEntity>>

    fun getPopular(apiKey: String, page: Int, language: String): Single<PaginationEntity<MovieEntity>>
}