package cn.dlc.commonlibrary.ui.base.mvp;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by John on 2017/8/28.
 */

public interface UiView {

    /**
     * 获取Activity
     *
     * @return
     */
    Activity getActivity();

    void startActivity(Class<? extends Activity> cls);

    void startActivityForResult(Class<? extends Activity> cls, int requestCode);

    void finishActivity();

    /**
     * 普通的显示Toast，时间为 {@link Toast#LENGTH_SHORT}
     *
     * @param resId
     */
    void showToast(int resId);

    /**
     * 普通的显示Toast，时间为 {@link Toast#LENGTH_SHORT}
     *
     * @param text
     */
    void showToast(String text);

    /**
     * 显示Toast，如果当前有Toast在显示,则复用之前的Toast，之前Toast的剩余显示时间会被忽略，重新计时 {@link Toast#LENGTH_SHORT}
     *
     * @param resId
     */
    void showOneToast(int resId);

    /**
     * 显示Toast，如果当前有Toast在显示,则复用之前的Toast，之前Toast的剩余显示时间会被忽略，重新计时 {@link Toast#LENGTH_SHORT}
     *
     * @param text
     */
    void showOneToast(String text);

    /**
     * 显示等待菊花对话框
     *
     * @param text 对话框文本
     * @param cancelable 是否可以点击外部取消对话框
     */
    void showWaitingDialog(String text, boolean cancelable);

    /**
     * 显示等待菊花对话框
     *
     * @param stringRes 对话框文本
     * @param cancelable 是否可以点击外部取消对话框
     */
    void showWaitingDialog(int stringRes, boolean cancelable);

    /**
     * 取消等待菊花对话框
     */
    void dismissWaitingDialog();

    /**
     * 判断界面是否已经运行 {@link Activity#onStart()}
     *
     * @return
     */
    boolean isActivityStarted();

    /**
     * 判断界面是否已经运行 {@link Activity#onResume()}
     *
     * @return
     */
    boolean isActivityResumed();
}
