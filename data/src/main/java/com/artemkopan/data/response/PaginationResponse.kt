package com.artemkopan.data.response

import com.google.gson.annotations.SerializedName

data class PaginationResponse<out Result>(

        @field:SerializedName("page")
        val page: Int,

        @field:SerializedName("total_pages")
        val totalPages: Int,

        @field:SerializedName("results")
        val results: List<Result>? = null,

        @field:SerializedName("total_results")
        val totalResults: Int
)