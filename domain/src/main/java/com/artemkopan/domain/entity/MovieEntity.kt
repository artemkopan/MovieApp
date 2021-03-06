package com.artemkopan.domain.entity

data class MovieEntity(

        val title: String? = null,

        val poster: PosterEntity? = null,

        val voteAverage: Double? = null,

        val voteAverageFormatted: String? = null,

        val id: Int? = null
)