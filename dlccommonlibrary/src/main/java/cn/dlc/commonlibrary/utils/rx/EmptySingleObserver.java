package cn.dlc.commonlibrary.utils.rx;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * 没有做任何处理的EmptySingleObserver
 */

public class EmptySingleObserver<T> implements SingleObserver<T> {
    
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onSuccess(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
