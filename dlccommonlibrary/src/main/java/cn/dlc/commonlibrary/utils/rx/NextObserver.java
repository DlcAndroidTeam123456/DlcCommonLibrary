package cn.dlc.commonlibrary.utils.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 就剩下onNext没实现的Observer
 */

public abstract class NextObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
