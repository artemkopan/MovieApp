package com.artemkopan.domain.entity

data class PaginationEntity<out Result>(

        val page: Int,

        val totalPages: Int,

        val totalResults: Int,

        val results: List<Result>? = null
)