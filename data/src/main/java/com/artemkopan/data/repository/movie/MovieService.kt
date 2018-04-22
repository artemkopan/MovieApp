package com.artemkopan.data.repository.movie

import com.artemkopan.data.response.MovieResponse
import com.artemkopan.data.response.PaginationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {


    @GET("movie/popular")
    fun getPopular(@Query("api_key") apiKey: String,
                   @Query("page") page: Int,
                   @Query("language") language: String): Single<PaginationResponse<MovieResponse>>


    @GET("movie/top_rated")
    fun getTopRated(@Query("api_key") apiKey: String,
                    @Query("page") page: Int,
                    @Query("language") language: String): Single<PaginationResponse<MovieResponse>>

//    @GET("movie/{id}")
//    fun getMovieDetail(@Path("id") id: Long,
//                       @Query("api_key") apiKey: String,
//                       @Query("append_to_response") append: AppendResponseBuilder,
//                       @Query("language") language: String = LANGUAGE): Single<DetailItem>

}