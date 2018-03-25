package com.artemkopan.movie;


import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.artemkopan.domain.utils.Logger;
import com.artemkopan.domain.utils.Logger.Printer;
import com.artemkopan.domain.utils.Logger.Priority;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

public class MovieApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initLogger();
    }

    private void initLogger() {
        if (com.artemkopan.presentation.BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
            Logger.addPrinter(new Printer() {
                @Override
                public void log(@Priority int priority, String tag, @NonNull String message,
                                @Nullable Throwable t) {
                    switch (priority) {
                        case Logger.DEBUG:
                            Timber.tag(tag).log(Log.DEBUG, t, message);
                            break;
                        case Logger.ERROR:
                            Timber.tag(tag).log(Log.ERROR, t, message);
                            break;
                        case Logger.INFO:
                            Timber.tag(tag).log(Log.INFO, t, message);
                            break;
                        case Logger.WARN:
                            Timber.tag(tag).log(Log.WARN, t, message);
                            break;
                        default:
                            Timber.tag(tag).log(priority, t, message);
                    }
                }
            });
        }
    }


}
