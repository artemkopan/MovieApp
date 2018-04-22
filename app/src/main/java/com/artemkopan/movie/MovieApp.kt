package com.artemkopan.movie


import android.app.Activity
import android.app.Application
import android.util.Log
import com.artemkopan.domain.utils.Logger
import com.artemkopan.domain.utils.Logger.Printer
import com.artemkopan.domain.utils.Logger.Priority
import com.artemkopan.domain.utils.TmdbImageModel
import com.artemkopan.movie.di.AppInjector
import com.artemkopan.movie.di.component.DaggerAppComponent
import com.artemkopan.presentation.misc.glide.TmdbImageModelFactory
import com.bumptech.glide.Glide
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.io.InputStream
import javax.inject.Inject


class MovieApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().application(this).build().inject(this)
        AppInjector(this).apply { registertCallbacks() }
        initLogger()
        initGlide()
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            Logger.addPrinter(LoggerTimberPrinter())
        } else {
            Logger.addPrinter(LoggerCrashlyticPrinter())
        }
    }

    private fun initGlide() {
        Glide.get(this)
                .registry
                .append(TmdbImageModel::class.java, InputStream::class.java, TmdbImageModelFactory())
    }

    private class LoggerTimberPrinter : Printer {

        override fun log(@Priority priority: Int, tag: String, message: String, t: Throwable?) {
            when (priority) {
                Logger.DEBUG -> Timber.tag(tag).log(Log.DEBUG, t, message)
                Logger.ERROR -> Timber.tag(tag).log(Log.ERROR, t, message)
                Logger.INFO -> Timber.tag(tag).log(Log.INFO, t, message)
                Logger.WARN -> Timber.tag(tag).log(Log.WARN, t, message)
                else -> Timber.tag(tag).log(priority, t, message)
            }
        }
    }

    private class LoggerCrashlyticPrinter : Printer {
        override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
