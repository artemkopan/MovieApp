package com.artemkopan.presentation.misc

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.State
import android.view.View

@Suppress("unused")
class SpaceItemDecoration : RecyclerView.ItemDecoration {

    private var spacing: Int = 0
    private var isHorizontal = false
    private var addFirst = true
    private var addLast = true

    constructor(spacing: Int) {
        this.spacing = spacing
    }

    constructor(spacing: Int, isHorizontal: Boolean) {
        this.spacing = spacing
        this.isHorizontal = isHorizontal
    }

    constructor(spacing: Int, isHorizontal: Boolean, addFirst: Boolean) {
        this.spacing = spacing
        this.isHorizontal = isHorizontal
        this.addFirst = addFirst
    }

    constructor(spacing: Int, isHorizontal: Boolean, addFirst: Boolean, addLast: Boolean) {
        this.spacing = spacing
        this.isHorizontal = isHorizontal
        this.addFirst = addFirst
        this.addLast = addLast
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State?) {
        val position = parent.getChildAdapterPosition(view)

        if (position == 0 && addFirst) {
            if (isHorizontal) {
                outRect.left = spacing
            } else {
                outRect.top = spacing
            }
        }

        if (!addLast) {
            if (position != parent.adapter.itemCount - 1) {
                if (isHorizontal) {
                    outRect.right = spacing
                } else {
                    outRect.bottom = spacing
                }
            }
        } else {
            if (isHorizontal) {
                outRect.right = spacing
            } else {
                outRect.bottom = spacing
            }
        }
    }
}