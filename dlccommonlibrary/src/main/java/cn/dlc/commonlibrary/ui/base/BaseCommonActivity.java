package cn.dlc.commonlibrary.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dlc.commonlibrary.R;
import cn.dlc.commonlibrary.ui.base.mvp.UiView;
import cn.dlc.commonlibrary.ui.dialog.WaitingDialog;
import cn.dlc.commonlibrary.utils.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * 基础Activity，建议继承此类再写一个BaseActivity
 */
public abstract class BaseCommonActivity extends RxAppCompatActivity implements UiView {

    private Unbinder mUnbinder;
    private WaitingDialog mWaitingDialog;

    /**
     * Activity调用过{@link #onStart()}
     */
    protected boolean mActivityStarted;
    /**
     * Activity调用过{@link #onResume()}
     */
    protected boolean mActivityResumed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        if (getLayoutID() != 0) {// 子类设置布局id使用,
            setContentView(getLayoutID());
        }

        if (useButterKnife()) {
            //所有的ButterKnife绑定让父类完成
            mUnbinder = ButterKnife.bind(this);
        }
    }

    /**
     * 在设置布局前执行,可以用来设置横竖屏、屏幕常亮、修改状态栏操作之类
     */
    protected void beforeSetContentView() {
        // 在设置布局前执行
    }

    @Override
    protected void onStart() {
        super.onStart();
        mActivityStarted = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityResumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivityResumed = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mActivityStarted = false;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        // 在解绑控件前，先释放，避免 NullPointerException
        onDestroyUnbindView();

        dismissWaitingDialog();

        if (useButterKnife()) {
            mUnbinder.unbind();//在页面销毁时解绑
        }
    }

    /**
     * 在{@link #onDestroy()}生命周期中，运行在 黄油刀 {@link Unbinder#unbind()} 之前，
     * 因为{@link Unbinder#unbind()}运行后，注入的View会置null
     * 某些特殊控件，如地图，需要明确的释放资源，可以重写此方法进行相关操作，避免出现 NullPointerException
     *
     * @see ButterKnife
     */
    protected void onDestroyUnbindView() {
        // mMapView.onDestroy(); 释放地图控件
    }

    /**
     * 5.0系统以上设置状态栏沉浸和透明
     */
    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView()
                .setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    /**
     * 6.0以上设置白的黑字状态栏
     */
    protected void setDarkStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView()
                .setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.dark_status));
        }
    }

    /**
     * 是否使用ButterKnife
     *
     * @return
     */
    protected boolean useButterKnife() {
        return true;
    }

    /**
     * 获取布局xml的id, 子类实现，可以为0
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutID();

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> cls, int requestCode) {
        startActivityForResult(new Intent(this, cls), requestCode);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showToast(int resId) {
        ToastUtil.show(this, resId);
    }

    @Override
    public void showToast(String text) {
        ToastUtil.show(this, text);
    }

    @Override
    public void showOneToast(int resId) {
        ToastUtil.showOne(this, resId);
    }

    @Override
    public void showOneToast(String text) {
        ToastUtil.showOne(this, text);
    }

    @Override
    public void showWaitingDialog(String text, boolean cancelable) {
        if (mWaitingDialog == null) {
            mWaitingDialog = WaitingDialog.newDialog(this).setMessage(text);
        }
        if (mWaitingDialog.isShowing()) {
            mWaitingDialog.dismiss();
        }

        mWaitingDialog.setMessage(text);

        mWaitingDialog.setCancelable(cancelable);
        mWaitingDialog.show();
    }

    @Override
    public void showWaitingDialog(int stringRes, boolean cancelable) {
        showWaitingDialog(getString(stringRes), cancelable);
    }

    @Override
    public void dismissWaitingDialog() {
        if (mWaitingDialog != null && mWaitingDialog.isShowing()) {
            mWaitingDialog.dismiss();
        }
    }

    @Override
    public boolean isActivityStarted() {
        return mActivityStarted;
    }

    @Override
    public boolean isActivityResumed() {
        return mActivityResumed;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();

            //如果不是落在EditText区域，则需要关闭输入法
            if (hideKeyboard(v, ev)) {
                InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private boolean hideKeyboard(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {

            int[] location = { 0, 0 };
            view.getLocationInWindow(location);

            //获取现在拥有焦点的控件view的位置，即EditText
            int left = location[0], top = location[1], bottom = top + view.getHeight(), right =
                left + view.getWidth();
            //判断我们手指点击的区域是否落在EditText上面，如果不是，则返回true，否则返回false
            boolean isInEt = (event.getX() > left
                && event.getX() < right
                && event.getY() > top
                && event.getY() < bottom);
            return !isInEt;
        }
        return false;
    }
}
