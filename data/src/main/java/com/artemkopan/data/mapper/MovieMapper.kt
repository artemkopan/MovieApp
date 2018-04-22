package com.artemkopan.data.mapper

import com.artemkopan.data.response.MovieResponse
import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.domain.entity.PosterEntity
import com.artemkopan.domain.utils.Mapper

class MovieMapper : Mapper<MovieEntity, MovieResponse>() {
    override fun map(from: MovieResponse) = MovieEntity(
            from.title,
            PosterEntity(from.posterPath),
            from.voteAverage,
            from.id
    )
}

