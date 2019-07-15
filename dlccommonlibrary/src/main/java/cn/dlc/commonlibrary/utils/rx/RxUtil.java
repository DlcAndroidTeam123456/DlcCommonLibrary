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
import io.reactivex.schedulers.Schedulers;

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
}
