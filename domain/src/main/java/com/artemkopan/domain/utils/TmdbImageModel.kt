package com.artemkopan.domain.utils

interface TmdbImageModel {

    fun requestUrl(width: Int, height: Int): String

}