package com.artemkopan.data.exceptions

import com.google.gson.Gson
import retrofit2.HttpException

class ApiException private constructor(message: String?, cause: Throwable?)
    : Exception(message, cause) {

    companion object {

        fun parse(throwable: Throwable, gson: Gson): Throwable {
            if (throwable !is HttpException) {
                return throwable
            }

            val responseBody = throwable.response().errorBody()

            if (responseBody != null) {
                //todo implement error parsing
            }

            return ApiException(throwable.message(), throwable)
        }
    }

}
