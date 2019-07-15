package cn.dlc.androidlibaray.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import cn.dlc.androidlibaray.BuildConfig;
import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.exception.ApiException;
import cn.dlc.commonlibrary.okgo.interceptor.ErrorInterceptor;
import cn.dlc.commonlibrary.okgo.logger.JsonRequestLogger;
import cn.dlc.commonlibrary.okgo.translator.DefaultErrorTranslator;
import cn.dlc.commonlibrary.utils.PrefUtil;
import cn.dlc.commonlibrary.utils.ResUtil;
import cn.dlc.commonlibrary.utils.ScreenUtil;
import cn.dlc.commonlibrary.utils.SystemUtil;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import okhttp3.OkHttpClient;

/**
 * Created by John on 2018/3/5.
 */

public class App extends Application {

    private static App sInstance;
    private Handler mUiHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mUiHandler = new Handler();
        if (SystemUtil.isMainProcess(this)) {

            ScreenUtil.init(this); // 获取屏幕尺寸
            ResUtil.init(this); // 资源
            PrefUtil.init(this); // SharedPreference

            // 网络
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
            OkGoWrapper.initOkGo(this, builder.build());
            OkGoWrapper.instance()
                // 错误信息再格式化
                .setErrorTranslator(new DefaultErrorTranslator())
                // 拦截网络错误，一般是登录过期啥的
                .setErrorInterceptor(new ErrorInterceptor() {
                    @Override
                    public boolean interceptException(Throwable tr) {

                        if (tr instanceof ApiException) {
                            ApiException ex = (ApiException) tr;
                            if (ex.getCode() == -2) {
                                // 登录信息过期，请重新登录
                                return true;
                            }
                        }
                        return false;
                    }
                })
                // 打印网络访问日志的
                .setRequestLogger(new JsonRequestLogger(BuildConfig.DEBUG, 30));
        }
    }

    public static App instance() {
        return sInstance;
    }

    public static Handler getUiHandler() {
        return instance().mUiHandler;
    }

    public static App getAppContext(Context context) {
        return (App) context.getApplicationContext();
    }
}
