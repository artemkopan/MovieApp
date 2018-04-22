package com.artemkopan.presentation.ui.list

import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.artemkopan.domain.entity.MovieEntity
import com.artemkopan.presentation.R
import com.artemkopan.presentation.base.recycler.BaseAdapter
import com.artemkopan.presentation.base.recycler.BaseHolder
import com.artemkopan.presentation.base.recycler.SimpleHolder
import com.artemkopan.presentation.extensions.inflateView
import javax.inject.Inject

class MovieListAdapter @Inject constructor()
    : BaseAdapter<MovieEntity, BaseHolder<MovieEntity>>(DIFF_CALLBACK) {

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<MovieEntity> {
        return MovieItemHolder(parent.inflateView(R.layout.item_movie))
    }

    override fun onCreateFooterViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<MovieEntity> {
        return SimpleHolder(parent.inflateView(R.layout.item_movie_progress))
    }

    companion object {

        @JvmStatic
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity?, newItem: MovieEntity?): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MovieEntity?, newItem: MovieEntity?): Boolean {
                return oldItem?.id == newItem?.id
            }
        }

    }

}
