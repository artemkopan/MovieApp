package com.artemkopan.movie.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.artemkopan.domain.utils.Logger
import com.artemkopan.presentation.base.Injectable
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

class AppInjector(private val application: Application) {

    fun registertCallbacks() {
        application
                .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                        handleActivity(activity)
                    }

                    override fun onActivityStarted(activity: Activity) {

                    }

                    override fun onActivityResumed(activity: Activity) {

                    }

                    override fun onActivityPaused(activity: Activity) {

                    }

                    override fun onActivityStopped(activity: Activity) {

                    }

                    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

                    }

                    override fun onActivityDestroyed(activity: Activity) {

                    }
                })
    }


    private fun handleActivity(activity: Activity) {
        if (activity is Injectable) {
            AndroidInjection.inject(activity)
        } else {
            Logger.i("Your activity doesn't injectable %s.\n" +
                    "Implement Injectable interface if you want injectable activity ")
        }
        if (activity is HasSupportFragmentInjector && activity is FragmentActivity) {
            activity.supportFragmentManager
                    .registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks(), true)
        }
    }

    private fun fragmentLifecycleCallbacks(): FragmentManager.FragmentLifecycleCallbacks {
        return object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentPreAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
                if (f is Injectable) {
                    AndroidSupportInjection.inject(f)
                } else {
                    Logger.i("Your fragment doesn't injectable %s.\n" +
                            "Implement Injectable interface if you want injectable activity ")
                }
            }
        }
    }
}
