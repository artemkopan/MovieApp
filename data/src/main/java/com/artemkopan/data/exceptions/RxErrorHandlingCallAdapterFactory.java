package com.artemkopan.data.exceptions;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import org.reactivestreams.Publisher;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    private final RxJava2CallAdapterFactory original;
    private final Gson gson;

    private RxErrorHandlingCallAdapterFactory(Gson gson, Scheduler scheduler) {
        this.gson = gson;
        this.original = RxJava2CallAdapterFactory.createWithScheduler(scheduler);
    }

    public static CallAdapter.Factory create(Gson gson, Scheduler scheduler) {
        return new RxErrorHandlingCallAdapterFactory(gson, scheduler);
    }

    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        //noinspection unchecked
        return new RxCallAdapterWrapper(original.get(returnType, annotations, retrofit));
    }

    private class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {

        private final CallAdapter<R, Object> wrapped;

        RxCallAdapterWrapper(CallAdapter<R, Object> wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings({"unchecked", "Convert2Lambda"})
        @Override
        public Object adapt(@NonNull Call<R> call) {
            Object adapt = wrapped.adapt(call);

            if (adapt instanceof Observable) {
                return ((Observable) adapt).onErrorResumeNext(new Function<Throwable, ObservableSource>() {
                    @Override
                    public ObservableSource apply(Throwable throwable) throws Exception {
                        return Observable.error(ApiException.Companion.parse(throwable, gson));
                    }
                });
            } else if (adapt instanceof Flowable) {
                return ((Flowable) adapt).onErrorResumeNext(new Function<Throwable, Publisher>() {
                    @Override
                    public Publisher apply(Throwable throwable) throws Exception {
                        return Flowable.error(ApiException.Companion.parse(throwable, gson));
                    }
                });
            } else if (adapt instanceof Single) {
                return ((Single) adapt).onErrorResumeNext(new Function<Throwable, SingleSource>() {
                    @Override
                    public SingleSource apply(Throwable throwable) throws Exception {
                        return Single.error(ApiException.Companion.parse(throwable, gson));
                    }
                });
            } else if (adapt instanceof Maybe) {
                return ((Maybe) adapt).onErrorResumeNext(new Function<Throwable, MaybeSource>() {
                    @Override
                    public MaybeSource apply(Throwable throwable) throws Exception {
                        return Maybe.error(ApiException.Companion.parse(throwable, gson));
                    }
                });
            } else if (adapt instanceof Completable) {
                return ((Completable) adapt).onErrorResumeNext(new Function<Throwable, CompletableSource>() {
                    @Override
                    public CompletableSource apply(Throwable throwable) throws Exception {
                        return Completable.error(ApiException.Companion.parse(throwable, gson));
                    }
                });
            } else {
                return adapt;
            }
        }
    }

}