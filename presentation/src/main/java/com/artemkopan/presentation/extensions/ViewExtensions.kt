package com.artemkopan.presentation.extensions

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnPreDrawListener


fun ViewGroup.inflateView(@LayoutRes res: Int): View = LayoutInflater.from(context)
        .inflate(res, this, false)!!

infix fun View.setGone(gone: Boolean) {
    this.visibility = if (gone) View.GONE else View.VISIBLE
}

infix fun View.setInvisible(invisible: Boolean) {
    this.visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

/**
 * @param callback Return true to proceed with the current drawing pass, or false to cancel.
 */
inline fun View.callOnPreDraw(crossinline callback: (View) -> Boolean) {
    this.viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            if (this@callOnPreDraw.viewTreeObserver.isAlive) {
                this@callOnPreDraw.viewTreeObserver.removeOnPreDrawListener(this)
                return callback(this@callOnPreDraw)
            }
            return true
        }
    })
}