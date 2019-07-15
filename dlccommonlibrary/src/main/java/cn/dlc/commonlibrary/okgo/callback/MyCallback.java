package cn.dlc.commonlibrary.okgo.callback;

import android.support.annotation.NonNull;
import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.converter.Convert2;
import cn.dlc.commonlibrary.okgo.interceptor.ErrorInterceptor;
import cn.dlc.commonlibrary.okgo.logger.RequestLogger;
import cn.dlc.commonlibrary.okgo.translator.ErrorTranslator;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * 二次封装的OkGo回调
 */

public abstract class MyCallback<T> extends AbsCallback<T> {

    private final OkGoWrapper mOkGoWrapper;
    private Request<T, ? extends Request> mRequest;
    protected Class<T> mClazz;
    private String mJson;

    public MyCallback() {
        mOkGoWrapper = OkGoWrapper.instance();
    }

    public void setClass(@NonNull Class<T> clazz) {
        mClazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        mRequest = request;
    }

    @Override
    public void onSuccess(Response<T> response) {
        try {
            beforeSuccessOrFailure();
            onSuccess(response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {

        mJson = Convert2.toString(response);

        if (mClazz == null) {
            throw new NullPointerException(
                "mClazz为null，必须调用 cn.dlc.commonlibrary.okgo.callback.MyCallback.setClass()");
        }

        T t = convert(mJson);
        // 打印日志
        RequestLogger requestLogger = mOkGoWrapper.getRequestLogger();
        if (requestLogger != null) {
            requestLogger.logRequest(mRequest.getUrl(), mRequest.getHeaders(), mRequest.getParams(),
                mJson, null);
        }
        return t;
    }

    @Override
    public void onError(Response<T> response) {
        Throwable exception = response.getException();
        // 打印日志
        RequestLogger requestLogger = mOkGoWrapper.getRequestLogger();
        if (requestLogger != null) {
            requestLogger.logRequest(mRequest.getUrl(), mRequest.getHeaders(), mRequest.getParams(),
                mJson, exception);
        }

        ErrorInterceptor errorInterceptor = mOkGoWrapper.getErrorInterceptor();
        // 拦截错误
        if (errorInterceptor != null && errorInterceptor.interceptException(exception)) {
            return;
        }

        String message;
        ErrorTranslator errorTranslator = mOkGoWrapper.getErrorTranslator();
        if (errorTranslator == null) {
            message = exception.getMessage();
        } else {
            message = errorTranslator.translate(exception);
        }

        try {
            beforeSuccessOrFailure();
            onFailure(message, exception);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把响应数据（一般为json文本）装换成实力类
     *
     * @param stringResponse
     * @return
     * @throws Throwable
     */
    protected abstract T convert(String stringResponse) throws Throwable;

    /**
     * 在成功{@link #onSuccess(Object)}或失败{@link #onFailure(String, Throwable)}之前执行，
     * 可用来统一执行取消菊花对话框之类的操作，从而避免繁琐地分别在成功和失败时执行此类操作。
     * 默认空实现。
     */
    public void beforeSuccessOrFailure() {

    }

    /**
     * 成功回调
     *
     * @param t 实体类
     */
    public abstract void onSuccess(T t);

    /**
     * 失败回调
     *
     * @param message 异常信息
     * @param tr 异常
     */
    public abstract void onFailure(String message, Throwable tr);
}
