package com.artemkopan.presentation.ui.list

import android.view.View
import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.recycler.BaseHolder
import com.artemkopan.presentation.extensions.dimen
import com.artemkopan.presentation.misc.loadClear
import com.artemkopan.presentation.misc.loadImage
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.extensions.CacheImplementation
import kotlinx.android.extensions.ContainerOptions
import kotlinx.android.synthetic.main.item_movie.*

@ContainerOptions(CacheImplementation.SPARSE_ARRAY)
class MovieItemHolder(containerView: View)
    : BaseHolder<MovieEntity>(containerView) {

    private val radius = containerView.context.dimen(R.dimen.corner_radius)

    override fun bind(item: MovieEntity) {
        thumbnailView.loadImage(
                model = item.poster,
                transformations = *arrayOf(RoundedCornersTransformation(radius, 0)))

        titleView.text = item.title
    }

    override fun bindClickListener(listener: View.OnClickListener) {
        containerView.setOnClickListener(listener)
    }

    override fun unbindClickListener() {
        containerView.setOnClickListener(null)
    }

    override fun recycled() {
        super.recycled()
        thumbnailView.loadClear()
    }

}