package com.artemkopan.domain.entity

import com.artemkopan.domain.Const
import com.artemkopan.domain.utils.TmdbImageModel
import java.util.*

data class PosterEntity(private val path: String?) : TmdbImageModel {

    override fun requestUrl(width: Int, height: Int): String {
        // added handling different sizes;
        return String.format(Locale.US, Const.Url.TMDB_IMG, path)
    }


}