//package com.artemkopan.data.repository.movie
//
//import android.content.Context
//import com.artemkopan.data.mapper.MovieMapper
//import com.artemkopan.data.mapper.PaginationMapper
//import com.artemkopan.data.response.MovieResponse
//import com.artemkopan.data.response.PaginationResponse
//import com.artemkopan.domain.Const
//import com.artemkopan.domain.entity.MovieEntity
//import com.artemkopan.domain.entity.PaginationEntity
//import com.artemkopan.domain.repository.movie.MovieRepository
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import io.reactivex.Single
//import javax.inject.Inject
//
//
//class MovieMockRepository @Inject constructor(private val context: Context,
//                                              private val gson: Gson) : MovieRepository {
//
//    override fun getTopRated(apiKey: String, page: Int, language: String): Single<PaginationEntity<MovieEntity>> {
//        return getMoviesSingle("top_rated.json")
//    }
//
//    override fun getPopular(apiKey: String, page: Int, language: String): Single<PaginationEntity<MovieEntity>> {
//        return getMoviesSingle("popular.json")
//    }
//
//
//    private fun getMoviesSingle(fileName: String): Single<PaginationEntity<MovieEntity>> {
//        return Single.fromCallable { readFromFile(fileName) }
//                .map {
//                    with(object : TypeToken<PaginationResponse<MovieResponse>>() {}.type) {
//                        gson.fromJson<PaginationResponse<MovieResponse>>(it, this)
//                    }
//                }
//                .map {
//                    PaginationMapper(MovieMapper(Const.Formatter.DECIMAL_FORMAT)
//                            .mapList(it.results ?: emptyList()))
//                            .map(it)
//                }
//    }
//
//    private fun readFromFile(fileName: String) = context.assets.open(fileName)
//            .bufferedReader()
//            .use {
//                it.readText()
//            }
//
//}