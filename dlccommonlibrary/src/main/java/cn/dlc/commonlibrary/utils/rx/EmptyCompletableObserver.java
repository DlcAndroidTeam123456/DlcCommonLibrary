package cn.dlc.commonlibrary.utils.rx;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;

/**
 * 没有做任何处理的CompletableObserver
 */

public class EmptyCompletableObserver implements CompletableObserver {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
