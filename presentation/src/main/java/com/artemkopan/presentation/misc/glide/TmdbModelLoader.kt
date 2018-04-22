package com.artemkopan.presentation.misc.glide

import com.artemkopan.domain.utils.TmdbImageModel
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader
import java.io.InputStream

class TmdbModelLoader(concreteLoader: ModelLoader<GlideUrl, InputStream>)
    : BaseGlideUrlLoader<TmdbImageModel>(concreteLoader) {

    override fun getUrl(model: TmdbImageModel?, width: Int, height: Int, options: Options?): String =
            model?.requestUrl(width, height) ?: ""

    override fun handles(model: TmdbImageModel): Boolean = true

}