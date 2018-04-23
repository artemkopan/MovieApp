package com.artemkopan.data.repository.movie

import com.artemkopan.data.mapper.MovieMapper
import com.artemkopan.data.mapper.PaginationMapper
import com.artemkopan.data.response.MovieResponse
import com.artemkopan.data.response.PaginationResponse
import com.artemkopan.domain.Const
import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.domain.entity.PaginationEntity
import com.artemkopan.domain.repository.movie.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class MovieDataRepository @Inject constructor(private val movieService: MovieService)
    : MovieRepository {

    override fun getTopRated(apiKey: String, page: Int, language: String): Single<PaginationEntity<MovieEntity>> {
        return movieService.getTopRated(apiKey, page, language).map(mapItems())
    }

    override fun getPopular(apiKey: String, page: Int, language: String): Single<PaginationEntity<MovieEntity>> {
        return movieService.getPopular(apiKey, page, language).map(mapItems())
    }

    private fun mapItems(): (PaginationResponse<MovieResponse>) -> PaginationEntity<MovieEntity> {
        return {
            PaginationMapper(MovieMapper(Const.Formatter.DECIMAL_FORMAT)
                    .mapList(it.results ?: emptyList()))
                    .map(it)
        }
    }
}