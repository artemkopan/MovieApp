package com.artemkopan.domain.utils;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Logger {

    private static final String TAG = "Logger";

    private static final List<Printer> PRINTERS = new ArrayList<>();

    /**
     * Priority constant for the println method;
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method;
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method;
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method;
     */
    public static final int ERROR = 6;

    public static void addPrinter(Printer printer) {
        PRINTERS.add(printer);
    }

    public static void removePrinter(Printer printer) {
        PRINTERS.remove(printer);
    }

    public static void d(String message) {
        d(message, (Throwable) null);
    }

    public static void d(String message, @Nullable Throwable t) {
        d(getTag(), message, t);
    }

    public static void d(String tag, @NonNull String message) {
        d(tag, message, null);
    }

    public static void d(String tag, @NonNull String message, @Nullable Throwable t) {
        for (Printer printer : PRINTERS) {
            printer.log(DEBUG, tag, message, t);
        }
    }

    public static void i(@NonNull String message) {
        i(message, (Throwable) null);
    }

    public static void i(@NonNull String message, @Nullable Throwable t) {
        i(getTag(), message, t);
    }

    public static void i(String tag, @NonNull String message) {
        i(tag, message, null);
    }

    public static void i(String tag, @NonNull String message, @Nullable Throwable t) {
        for (Printer printer : PRINTERS) {
            printer.log(INFO, tag, message, t);
        }
    }

    public static void w(@NonNull String message) {
        w(message, (Throwable) null);
    }

    public static void w(@NonNull String message, @Nullable Throwable t) {
        w(getTag(), message, t);
    }

    public static void w(String tag, @NonNull String message) {
        w(tag, message, null);
    }

    public static void w(String tag, @NonNull String message, @Nullable Throwable t) {
        for (Printer printer : PRINTERS) {
            printer.log(WARN, tag, message, t);
        }
    }

    public static void e(@NonNull String message) {
        e(message, (Throwable) null);
    }

    public static void e(@NonNull String message, @Nullable Throwable t) {
        e(getTag(), message, t);
    }

    public static void e(String tag, @NonNull String message) {
        e(tag, message, null);
    }

    public static void e(String tag, @NonNull String message, @Nullable Throwable t) {
        for (Printer printer : PRINTERS) {
            printer.log(ERROR, tag, message, t);
        }
    }

    private static String getTag() {
        String tag = TAG;

        if (PRINTERS.size() == 0) return tag;

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length > 3) {
            StackTraceElement stackTraceElement = stackTrace[5];
            String fileName = stackTraceElement.getFileName();
            if (fileName == null) fileName = "";
            tag = "(" + fileName + ":" + stackTraceElement.getLineNumber() + ") " + stackTraceElement.getMethodName();
        }

        return tag;
    }

    public interface Printer {

        void log(@Priority int priority, String tag, @NonNull String message, @Nullable Throwable t);

    }

    @IntDef(value = {
            DEBUG,
            INFO,
            WARN,
            ERROR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Priority {

    }
}
