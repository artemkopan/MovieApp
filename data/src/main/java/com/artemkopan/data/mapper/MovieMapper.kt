package com.artemkopan.data.mapper

import com.artemkopan.data.response.MovieResponse
import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.domain.entity.PosterEntity
import com.artemkopan.domain.utils.Mapper
import java.text.DecimalFormat

class MovieMapper(private val voteAverageFormatter: DecimalFormat)
    : Mapper<MovieEntity, MovieResponse>() {

    override fun map(from: MovieResponse) = MovieEntity(
            from.title,
            PosterEntity(from.posterPath),
            from.voteAverage,
            voteAverageFormatter.format(from.voteAverage ?: 0),
            from.id
    )
}

