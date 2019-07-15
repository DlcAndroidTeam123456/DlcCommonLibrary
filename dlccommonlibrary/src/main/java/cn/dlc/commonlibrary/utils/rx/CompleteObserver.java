package cn.dlc.commonlibrary.utils.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 就剩下onComplete没实现的Observer
 */

public abstract class CompleteObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
