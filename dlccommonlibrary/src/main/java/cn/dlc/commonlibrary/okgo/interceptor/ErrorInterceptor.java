package cn.dlc.commonlibrary.okgo.interceptor;

import cn.dlc.commonlibrary.okgo.callback.MyCallback;
import cn.dlc.commonlibrary.okgo.rx.OkObserver;

/**
 * 异常拦截器，用在拦截token异常，要重新登录之类的
 */
public interface ErrorInterceptor {

    /**
     * 拦截异常，
     *
     * @param tr 异常
     * @return 如果返回true，则表示异常被拦截住，不会走{@link MyCallback#onFailure(java.lang.String, * java.lang.Throwable)} 或者 {@link OkObserver#onFailure(java.lang.String, java.lang.Throwable)}
     */
    boolean interceptException(Throwable tr);
}
