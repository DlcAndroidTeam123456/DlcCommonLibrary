package cn.dlc.commonlibrary.okgo;

import android.app.Application;
import android.support.annotation.Nullable;
import cn.dlc.commonlibrary.okgo.callback.MyCallback;
import cn.dlc.commonlibrary.okgo.converter.Bean01Convert;
import cn.dlc.commonlibrary.okgo.converter.Convert2;
import cn.dlc.commonlibrary.okgo.converter.MyConverter;
import cn.dlc.commonlibrary.okgo.interceptor.ErrorInterceptor;
import cn.dlc.commonlibrary.okgo.logger.RequestLogger;
import cn.dlc.commonlibrary.okgo.translator.ErrorTranslator;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpMethod;
import com.lzy.okgo.model.HttpParams;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * OkGo包装类
 */
public class OkGoWrapper {

    private ErrorInterceptor mErrorInterceptor;
    private ErrorTranslator mErrorTranslator;
    private RequestLogger mRequestLogger;

    private OkGoWrapper() {
    }

    private static class InstanceHolder {
        private static final OkGoWrapper sInstance = new OkGoWrapper();
    }

    public static OkGoWrapper instance() {
        return InstanceHolder.sInstance;
    }

    /**
     * 检查是否已经初始化OkGo
     *
     * @throws IllegalStateException 没有初始化时抛出此异常，需先调用 {@link #initOkGo(Application, OkHttpClient)}
     */
    private void checkOkGo() {
        try {
            OkGo.getInstance().getContext();
        } catch (Exception e) {
            throw new IllegalStateException(
                "OkGo未初始化，必须先调用cn.dlc.commonlibrary.okgo.OkGoWrapper.initOkGo()");
        }
    }

    /**
     * 初始化OkGo
     * <pre>
     * {@code
     *
     * // 初始化实例
     * OkHttpClient.Builder builder = new OkHttpClient.Builder();
     * builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
     * OkGoWrapper.initOkGo(this, builder.build());
     * }
     * </pre>
     *
     * @param app {@link Application}实例
     * @param okHttpClient {@link OkHttpClient}实例
     * @see OkHttpClient.Builder
     */
    public static void initOkGo(Application app, OkHttpClient okHttpClient) {
        OkGo okGo = OkGo.getInstance().init(app);
        if (okHttpClient != null) {
            okGo.setOkHttpClient(okHttpClient);
        }
    }

    /**
     * 设置异常拦截器
     *
     * @param errorInterceptor 异常拦截器
     * @return OkGo包裹类
     * @see ErrorInterceptor
     */
    public OkGoWrapper setErrorInterceptor(ErrorInterceptor errorInterceptor) {
        mErrorInterceptor = errorInterceptor;
        return this;
    }

    /**
     * 设置异常格式化器
     *
     * @param errorTranslator 异常格式化器
     * @return OkGo包裹类
     * @see ErrorTranslator
     */
    public OkGoWrapper setErrorTranslator(ErrorTranslator errorTranslator) {
        mErrorTranslator = errorTranslator;
        return this;
    }

    /**
     * 设置请求日志打印工具
     *
     * @param requestLogger 日志打印工具
     * @return
     * @see RequestLogger
     */
    public OkGoWrapper setRequestLogger(RequestLogger requestLogger) {
        mRequestLogger = requestLogger;
        return this;
    }

    /**
     * 获取异常拦截器
     *
     * @return 异常拦截器
     */
    public @Nullable
    ErrorInterceptor getErrorInterceptor() {
        return mErrorInterceptor;
    }

    /**
     * 获取异常格式化器
     *
     * @return 异常格式化器
     */
    public @Nullable
    ErrorTranslator getErrorTranslator() {
        return mErrorTranslator;
    }

    /**
     * 获取请求日志打印工具
     *
     * @return 请求日志打印工具
     */
    public RequestLogger getRequestLogger() {
        return mRequestLogger;
    }

    /**
     * 进行异步请求
     *
     * @param httpMethod 请求方法，如GET、POST等，{@link HttpMethod}
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param callback 请求回调
     * @param tag 请求标签，默认为填进去的clazz参数，用以标识当前的请求，方便后续取消对应的请求，
     * @param <T> 响应实体类型
     */
    public <T> void asyncRequest(HttpMethod httpMethod, final String url,
        @Nullable final HttpHeaders headers, @Nullable final HttpParams params, Class<T> clazz,
        MyCallback<T> callback, @Nullable Object tag) {

        checkOkGo();

        tag = tag == null ? clazz : tag;

        if (clazz != null) {
            callback.setClass(clazz);
        }

        switch (httpMethod) {
            case GET:
                OkGo.<T>get(url).headers(headers).params(params).tag(tag).execute(callback);
                break;
            default:
                OkGo.<T>post(url).headers(headers).params(params).tag(tag).execute(callback);
                break;
        }
    }

    /**
     * POST请求
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param callback 请求回调
     * @param tag 请求标签，默认为填进去的clazz参数，用以标识当前的请求，方便后续取消对应的请求，
     * @param <T> 响应实体类型
     */
    public <T> void post(String url, @Nullable HttpHeaders headers, @Nullable HttpParams params,
        Class<T> clazz, MyCallback<T> callback, @Nullable Object tag) {
        asyncRequest(HttpMethod.POST, url, headers, params, clazz, callback, tag);
    }

    /**
     * POST请求
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param callback 请求回调
     * @param <T> 响应实体类型
     */
    public <T> void post(String url, @Nullable HttpHeaders headers, @Nullable HttpParams params,
        Class<T> clazz, MyCallback<T> callback) {
        post(url, headers, params, clazz, callback, null);
    }

    /**
     * POST请求
     *
     * @param url 请求url
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param callback 请求回调
     * @param <T> 响应实体类型
     */
    public <T> void post(String url, @Nullable HttpParams params, Class<T> clazz,
        MyCallback<T> callback) {
        post(url, null, params, clazz, callback);
    }

    /**
     * GET请求
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param callback 请求回调
     * @param tag 请求标签，默认为填进去的clazz参数，用以标识当前的请求，方便后续取消对应的请求，
     * @param <T> 响应实体类型
     */
    public <T> void get(String url, @Nullable HttpHeaders headers, @Nullable HttpParams params,
        Class<T> clazz, MyCallback<T> callback, @Nullable Object tag) {
        asyncRequest(HttpMethod.GET, url, headers, params, clazz, callback, tag);
    }

    /**
     * GET请求
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param callback 请求回调
     * @param <T> 响应实体类型
     */
    public <T> void get(String url, @Nullable HttpHeaders headers, @Nullable HttpParams params,
        Class<T> clazz, MyCallback<T> callback) {
        get(url, headers, params, clazz, callback, null);
    }

    /**
     * GET请求
     *
     * @param url 请求url
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param callback 请求回调
     * @param <T> 响应实体类型
     */
    public <T> void get(String url, @Nullable HttpParams params, Class<T> clazz,
        MyCallback<T> callback) {
        get(url, null, params, clazz, callback);
    }

    /**
     * 取消所有请求
     */
    public void cancelAll() {
        OkGo.getInstance().cancelAll();
    }

    /**
     * 通过tag取消请求
     *
     * @param tag 请求的时候设置的tag
     */
    public void cancelTag(Object tag) {
        OkGo.getInstance().cancelTag(tag);
    }

    /**
     * 同步请求
     *
     * @param httpMethod 请求方法，如GET、POST等，{@link HttpMethod}
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param tag 请求标签，默认为填进去的clazz参数，用以标识当前的请求，方便后续取消对应的请求，
     * @throws IOException
     */
    private Response syncRequest(HttpMethod httpMethod, final String url,
        @Nullable final HttpHeaders headers, @Nullable final HttpParams params,
        @Nullable Object tag) throws IOException {

        checkOkGo();

        switch (httpMethod) {
            case GET:
                return OkGo.get(url).headers(headers).params(params).tag(tag).execute();
            default:
                return OkGo.post(url).headers(headers).params(params).tag(tag).execute();
        }
    }

    /**
     * 同步请求
     *
     * @param httpMethod 请求方法，如GET、POST等，{@link HttpMethod}
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param converter 返回类型转换器
     * @param tag 请求标签，默认为填进去的clazz参数，用以标识当前的请求，方便后续取消对应的请求
     * @param <T> 返回类型
     * @return
     * @throws Throwable
     */
    public <T> T syncRequest(HttpMethod httpMethod, String url, @Nullable HttpHeaders headers,
        @Nullable HttpParams params, @android.support.annotation.NonNull MyConverter<T> converter,
        @Nullable Object tag) throws Throwable {

        String json = null;

        try {

            Object finalTag = tag == null ? converter.getToConvertClass() : tag;

            Response response = syncRequest(httpMethod, url, headers, params, finalTag);

            int responseCode = response.code();

            // 服务器错误
            if (responseCode == 404 || responseCode >= 500) {
                throw HttpException.NET_ERROR();
            }

            json = Convert2.toString(response);

            T t = converter.convert(json);

            // 打印成功的日志
            if (mRequestLogger != null) {
                mRequestLogger.logRequest(url, headers, params, json, null);
            }

            return t;
        } catch (Throwable e) {
            // 打印失败时的日志
            if (mRequestLogger != null) {
                mRequestLogger.logRequest(url, headers, params, json, e);
            }
            //e.printStackTrace();
            throw e;
        }
    }

    /**
     * 通过rx请求
     *
     * @param httpMethod 请求方法，如GET、POST等，{@link HttpMethod}
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param tag 请求标签，默认为填进去的clazz参数，用以标识当前的请求，方便后续取消对应的请求，
     * @param converter 返回类型转换器
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxRequest(final HttpMethod httpMethod, final String url,
        @Nullable final HttpHeaders headers, @Nullable final HttpParams params,
        @android.support.annotation.NonNull final MyConverter<T> converter,
        @Nullable final Object tag) {

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {

                try {
                    T t = syncRequest(httpMethod, url, headers, params, converter, tag);
                    emitter.onNext(t);
                } catch (Throwable throwable) {
                    if (!emitter.isDisposed()) {
                        emitter.onError(throwable);
                        return;
                    }
                }

                emitter.onComplete();
            }
        }).compose(this.<T>interceptError());
    }

    /**
     * 通过rx请求
     *
     * @param httpMethod http请求方法
     * @param url
     * @param params
     * @param converter 返回类型转换器
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxRequest(final HttpMethod httpMethod, final String url,
        @Nullable final HttpParams params,
        @android.support.annotation.NonNull final MyConverter<T> converter, @Nullable Object tag) {

        return rxRequest(httpMethod, url, null, params, converter, tag);
    }

    /**
     * 拦截错误
     *
     * @param <T>
     * @return
     */
    private <T> ObservableTransformer<T, T> interceptError() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(
                @io.reactivex.annotations.NonNull Observable<T> upstream) {

                return upstream.onErrorResumeNext(
                    new Function<Throwable, ObservableSource<? extends T>>() {
                        @Override
                        public ObservableSource<? extends T> apply(@NonNull Throwable throwable)
                            throws Exception {
                            if (mErrorInterceptor != null && mErrorInterceptor.interceptException(
                                throwable)) {
                                return Observable.empty();
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    });
            }
        };
    }

    /**
     * rx方式的POST请求
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param tag 请求标签，默认为填进去的clazz参数，用以标识当前的请求，方便后续取消对应的请求，
     * @param converter 返回类型转换器
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxPost(String url, @Nullable HttpHeaders headers,
        @Nullable HttpParams params, @android.support.annotation.NonNull MyConverter<T> converter,
        @Nullable Object tag) {
        return rxRequest(HttpMethod.POST, url, headers, params, converter, tag);
    }

    /**
     * rx方式的POST请求
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param converter 返回类型转换器
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxPost(String url, @Nullable HttpHeaders headers,
        @Nullable HttpParams params, @android.support.annotation.NonNull MyConverter<T> converter) {
        return rxPost(url, headers, params, converter, null);
    }

    /**
     * rx方式的POST请求
     *
     * @param url 请求url
     * @param params 请求参数
     * @param converter 返回类型转换器
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxPost(String url, @Nullable HttpParams params,
        @android.support.annotation.NonNull MyConverter<T> converter) {
        return rxPost(url, null, params, converter, null);
    }

    /**
     * rx方式的post请求，并做code=1处理
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxPostBean01(String url, @Nullable HttpHeaders headers,
        @Nullable HttpParams params, final Class<T> clazz) {

        return rxPost(url, headers, params, new Bean01Convert<>(clazz));
    }

    /**
     * rx方式的post请求，并做code=1处理
     *
     * @param url 请求url
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxPostBean01(String url, @Nullable HttpParams params,
        final Class<T> clazz) {
        return rxPostBean01(url, null, params, clazz);
    }

    /**
     * rx方式的GET请求
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param tag 请求标签，默认为填进去的clazz参数，用以标识当前的请求，方便后续取消对应的请求，
     * @param converter 返回类型转换器
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxGet(String url, @Nullable HttpHeaders headers,
        @Nullable HttpParams params, @android.support.annotation.NonNull MyConverter<T> converter,
        @Nullable Object tag) {
        return rxRequest(HttpMethod.GET, url, headers, params, converter, tag);
    }

    /**
     * rx方式的GET请求
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param converter 返回类型转换器
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxGet(String url, @Nullable HttpHeaders headers,
        @Nullable HttpParams params, @android.support.annotation.NonNull MyConverter<T> converter) {
        return rxGet(url, headers, params, converter, null);
    }

    /**
     * rx方式的GET请求
     *
     * @param url 请求url
     * @param params 请求参数
     * @param converter 返回类型转换器
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxGet(String url, @Nullable HttpParams params,
        @android.support.annotation.NonNull MyConverter<T> converter) {
        return rxGet(url, null, params, converter);
    }

    /**
     * rx方式的GET请求，并做code=1处理
     *
     * @param url 请求url
     * @param headers 请求头
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxGetBean01(String url, @Nullable HttpHeaders headers,
        @Nullable HttpParams params, Class<T> clazz) {
        return rxGet(url, headers, params, new Bean01Convert<>(clazz));
    }

    /**
     * rx方式的GET请求，并做code=1处理
     *
     * @param url 请求url
     * @param params 请求参数
     * @param clazz 响应实体类
     * @param <T> 返回类型
     * @return
     */
    public <T> Observable<T> rxGetBean01(String url, @Nullable HttpParams params, Class<T> clazz) {
        return rxGetBean01(url, null, params, clazz);
    }

    /**
     * 测试用代码
     *
     * @return
     */
    public String testSnapshot() {
        return "test";
    }
}
