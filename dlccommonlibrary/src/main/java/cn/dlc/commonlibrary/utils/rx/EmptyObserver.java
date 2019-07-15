package cn.dlc.commonlibrary.utils.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 没有做任何处理的Observer
 */

public class EmptyObserver implements Observer<Object> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(Object o) {

    }
}
