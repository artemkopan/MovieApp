package com.artemkopan.movie.di.component

import android.app.Application
import com.artemkopan.movie.MovieApp
import com.artemkopan.movie.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    NetworkModule::class,
    DataModule::class,
    InteractorModule::class,
    ViewModelModule::class])
interface AppComponent {

    fun inject(movieApp: MovieApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


}

