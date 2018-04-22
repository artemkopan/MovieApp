package com.artemkopan.presentation.base

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.artemkopan.domain.utils.Logger
import dagger.Lazy
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity(), HasSupportFragmentInjector {

    protected val destroyDisposable = CompositeDisposable()
    protected lateinit var viewModel: ViewModel

    @Inject
    protected lateinit var viewModelFactory: Lazy<ViewModelProvider.Factory>

    @Inject
    internal lateinit var fragmentInjector: Lazy<DispatchingAndroidInjector<Fragment>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        registerViewModelLifecycle()
        if (getContentView() != View.NO_ID) {
            setContentView(getContentView())
        }
        onCreated(savedInstanceState)
    }

    override fun onDestroy() {
        destroyDisposable.clear()
        unregisterViewModelLifecycle()
        super.onDestroy()
    }

    protected abstract fun onCreated(savedInstanceState: Bundle?)

    @LayoutRes
    protected abstract fun getContentView(): Int

    protected abstract fun getViewModelClass(): Class<ViewModel>

    /**
     * If you don't want to init view model, you can override method and stay empty
     */
    protected fun initViewModel() {
        if (!::viewModelFactory.isInitialized) {
            Logger.w("ViewModelFactory is not initialized! Please, check your dagger inject logic.")
            return
        }
        viewModel = ViewModelProviders.of(this, viewModelFactory.get())
                .get(getViewModelClass())
    }

    protected fun registerViewModelLifecycle() {
        if (::viewModel.isInitialized) {
            lifecycle.addObserver(viewModel)
        }
    }

    protected fun unregisterViewModelLifecycle() {
        if (::viewModel.isInitialized) {
            lifecycle.removeObserver(viewModel)
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector.get()

}