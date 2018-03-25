package com.artemkopan.presentation.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.artemkopan.domain.utils.Logger
import dagger.Lazy
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity() {

    protected lateinit var viewModel: ViewModel

    @Inject
    protected lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        if (getContentView() != View.NO_ID) {
            setContentView(getContentView())
        }
    }

    @LayoutRes
    protected abstract fun getContentView(): Int

    protected abstract fun getViewModelClass(): Class<ViewModel>

    /**
     * If you don't want to init view model, you can override method and stay empty
     */
    protected fun initViewModel() {
        if (this::viewModelFactory.isInitialized) {
            Logger.w("ViewModelFactory is not initialized! Please, check your dagger inject logic.")
            return
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory.get()).get(getViewModelClass())
    }
}