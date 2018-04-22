package com.artemkopan.movie.di.module

import android.content.Context
import com.artemkopan.data.exceptions.RxErrorHandlingCallAdapterFactory
import com.artemkopan.data.repository.movie.MovieService
import com.artemkopan.domain.Const
import com.artemkopan.domain.utils.Logger
import com.artemkopan.movie.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @JvmStatic
    @Provides
    fun provideGson() = Gson()

    @JvmStatic
    @Provides
    internal fun provideOkhttp(context: Context): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(StethoInterceptor())

            val logger = HttpLoggingInterceptor.Logger { message ->
                Logger.d(Const.Tag.NETWORK, message)
            }

            clientBuilder.addNetworkInterceptor(HttpLoggingInterceptor(logger)
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return clientBuilder.build()
    }

    @JvmStatic
    @Provides
    internal fun provideRetrofit(gson: Gson, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create(gson, Schedulers.trampoline()))
                .build()
    }

    @JvmStatic
    @Provides
    internal fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

}