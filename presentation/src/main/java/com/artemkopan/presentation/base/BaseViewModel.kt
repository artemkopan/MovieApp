package com.artemkopan.presentation.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseViewModel : ViewModel() {

    protected val clearDisposable = CompositeDisposable()

    override fun onCleared() {
        clearDisposable.clear()
    }

}