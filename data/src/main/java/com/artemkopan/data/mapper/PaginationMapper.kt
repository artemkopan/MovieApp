package com.artemkopan.data.mapper

import com.artemkopan.data.response.PaginationResponse
import com.artemkopan.domain.entity.PaginationEntity
import com.artemkopan.domain.utils.Mapper

class PaginationMapper<ResultTo>(private val result: List<ResultTo>)
    : Mapper<PaginationEntity<ResultTo>, PaginationResponse<*>>() {
    override fun map(from: PaginationResponse<*>) = PaginationEntity(
            from.page,
            from.totalPages,
            from.totalResults,
            result
    )

}