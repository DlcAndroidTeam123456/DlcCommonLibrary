package cn.dlc.commonlibrary.utils.rx;

import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class RxUtil {

    /**
     * 统一线程处理，在IO线程订阅，在UI线程接收发射的数据
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxIoMain() {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理，在IO线程订阅，在UI线程接收发射的数据
     *
     * @param <T>
     * @return
     */
    public static <T> SingleTransformer<T, T> rxSingleIoMain() {    //compose简化线程
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(@io.reactivex.annotations.NonNull Single<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * IO线程调度
     *
     * @return
     */
    public static Scheduler io() {
        return Schedulers.io();
    }

    /**
     * 主线程（UI线程）调度
     *
     * @return
     */
    public static Scheduler main() {
        return AndroidSchedulers.mainThread();
    }

    /**
     * 重试
     *
     * @param delay
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> retry(final long delay) {
        return new ObservableTransformer<T, T>() {
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.retryWhen(
                    new Function<Observable<Throwable>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Observable<Throwable> throwableObservable)
                            throws Exception {
                            return throwableObservable.delay(delay, TimeUnit.MILLISECONDS);
                        }
                    });
            }
        };
    }

    /**
     * 重试一定次数
     *
     * @param maxTimes 重试次数
     * @param delay 重试延时
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> retry(final int maxTimes, final long delay) {

        final AtomicInteger count = new AtomicInteger();

        return new ObservableTransformer<T, T>() {
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.retry()
                    .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Observable<Throwable> throwableObservable)
                            throws Exception {
                            return throwableObservable.flatMap(
                                new Function<Throwable, ObservableSource<?>>() {
                                    @Override
                                    public ObservableSource<?> apply(Throwable throwable)
                                        throws Exception {
                                        if (count.getAndIncrement() < maxTimes) {
                                            return Observable.timer(delay, TimeUnit.MILLISECONDS);
                                        } else {
                                            return Observable.error(throwable);
                                        }
                                    }
                                });
                        }
                    });
            }
        };
    }

    /**
     * 重复执行
     *
     * @param delay
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> repeat(final long delay) {
        return new ObservableTransformer<T, T>() {
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                    // 一定时间后重新查询
                    .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Observable<Object> objectObservable)
                            throws Exception {
                            return objectObservable.delay(delay, TimeUnit.MILLISECONDS);
                        }
                    });
            }
        };
    }

    /**
     * 重试和重复执行
     *
     * @param retryDelay 重试延迟
     * @param repeatDelay 重复延迟
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> retryRepeat(final long retryDelay,
        final long repeatDelay) {
        return new ObservableTransformer<T, T>() {
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.retryWhen(
                    new Function<Observable<Throwable>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Observable<Throwable> throwableObservable)
                            throws Exception {
                            return throwableObservable.delay(retryDelay, TimeUnit.MILLISECONDS);
                        }
                    })
                    // 一定时间后重新查询
                    .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                        @Override
                        public ObservableSource<?> apply(Observable<Object> objectObservable)
                            throws Exception {
                            return objectObservable.delay(repeatDelay, TimeUnit.MILLISECONDS);
                        }
                    });
            }
        };
    }
}
