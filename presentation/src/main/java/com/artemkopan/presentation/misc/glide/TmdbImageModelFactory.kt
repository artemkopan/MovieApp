package com.artemkopan.presentation.misc.glide

import com.artemkopan.domain.utils.TmdbImageModel
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream

class TmdbImageModelFactory : ModelLoaderFactory<TmdbImageModel, InputStream> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<TmdbImageModel, InputStream> {
        return TmdbModelLoader(multiFactory.build(GlideUrl::class.java, InputStream::class.java))
    }

    override fun teardown() {}

}