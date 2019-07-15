package cn.dlc.commonlibrary.okgo.rx;

import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.translator.ErrorTranslator;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * okgo网络请求用的rxjava观察者
 */
public abstract class OkObserver<T> implements Observer<T> {

    private final OkGoWrapper mOkGoWrapper;

    /**
     * okgo网络请求用的rxjava观察者
     */
    public OkObserver() {
        mOkGoWrapper = OkGoWrapper.instance();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    /**
     * 成功回调
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 失败回调
     *
     * @param message 异常信息
     * @param tr 异常
     */
    public abstract void onFailure(String message, Throwable tr);

    @Override
    public void onError(@NonNull Throwable e) {
        String message;
        ErrorTranslator errorTranslator = mOkGoWrapper.getErrorTranslator();
        if (errorTranslator == null) {
            message = e.getMessage();
        } else {
            message = errorTranslator.translate(e);
        }
        onFailure(message, e);
    }

    @Override
    public void onComplete() {
        // 默认不用管
    }
}
