package cn.dlc.commonlibrary.okgo.sample_api;

import cn.dlc.commonlibrary.okgo.OkGoWrapper;
import cn.dlc.commonlibrary.okgo.callback.Bean01Callback;
import cn.dlc.commonlibrary.okgo.rx.OkObserver;
import cn.dlc.commonlibrary.ui.base.BaseCommonActivity;
import com.lzy.okgo.model.HttpParams;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 用来复制粘贴做模板的类，用来参考的
 */
public class SampleApi {

    private final OkGoWrapper mOkGoWrapper;

    private SampleApi() {
        mOkGoWrapper = OkGoWrapper.instance();
    }

    private static class InstanceHolder {
        private static final SampleApi sInstance = new SampleApi();
    }

    public static SampleApi get() {
        return InstanceHolder.sInstance;
    }

    //邮箱注册,用来看的，不要用
    public void mailRegist(String user_login, String user_pas, String code,
        Bean01Callback<StringBuilder> callback) {
        HttpParams Params = new HttpParams();
        Params.put("user_login", user_login);
        Params.put("user_pass", user_pas);
        Params.put("code", code);
        mOkGoWrapper.post("MAIL_REGIST_URL", null, Params, StringBuilder.class, callback);
    }

    //rx邮箱注册,用来看的，不要用
    public Observable<StringBuilder> rxMailRegist(String user_login, String user_pas, String code) {
        HttpParams Params = new HttpParams();
        Params.put("user_login", user_login);
        Params.put("user_pass", user_pas);
        Params.put("code", code);
        return mOkGoWrapper.rxPostBean01("MAIL_REGIST_URL", Params, StringBuilder.class);
    }

    // rx方式的调用例子
    private void testRxRequst(final BaseCommonActivity activity) {
        // 先弹个等待对话框，不准取消
        activity.showWaitingDialog("正在请求", false);
        rxMailRegist("账号1", "账号密码", "验证码")
            // 切换UI线程
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
            .subscribe(new OkObserver<Object>() {
                @Override
                public void onSuccess(Object o) {
                    // 取消对话框
                    activity.dismissWaitingDialog();
                    // 更新界面什么的
                }

                @Override
                public void onFailure(String message, Throwable tr) {
                    // 取消对话框
                    activity.dismissWaitingDialog();
                }
            });
    }
}
