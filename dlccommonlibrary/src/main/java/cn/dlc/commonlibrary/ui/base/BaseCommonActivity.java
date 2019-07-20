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
import cn.dlc.commonlibrary.ui.dialog.WaitingDialogImpl;
import cn.dlc.commonlibrary.utils.BindEventBus;
import cn.dlc.commonlibrary.utils.ToastUtil;
import me.yokeyword.fragmentation.ISupportActivity;
import org.greenrobot.eventbus.EventBus;

/**
 * 基础Activity，建议继承此类再写一个BaseActivity
 */
public abstract class BaseCommonActivity extends BaseFragmentationActivity
    implements UiView, ISupportActivity {

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

    /**
     * 获取布局xml的id, 子类实现，可以为0
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        if (getLayoutId() != 0) {// 子类设置布局id使用,
            setContentView(getLayoutId());
        }

        if (useButterKnife()) {
            //所有的ButterKnife绑定让父类完成
            mUnbinder = ButterKnife.bind(this);
        }

        afterSetContentView();

        if (toRegisterEventBus()) {
            registerEventBus();
        }
    }

    /**
     * 在设置布局前执行,可以用来设置横竖屏、屏幕常亮、修改状态栏操作之类
     */
    protected void beforeSetContentView() {
        // 在设置布局前执行
    }

    /**
     * 在设置布局后执行,用来初始化特殊的逻辑
     */
    private void afterSetContentView() {
        // 空实现
    }

    /**
     * 需要注册EventBus
     *
     * @return
     */
    protected boolean toRegisterEventBus() {
        return this.getClass().isAnnotationPresent(BindEventBus.class);
    }

    /**
     * 注册EventBus
     */
    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 反注册EventBus
     */
    protected void unregisterEventBus() {
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
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

        unregisterEventBus();
        dismissWaitingDialog();
        super.onDestroy();
        // 在解绑控件前，先释放，避免 NullPointerException
        onDestroyUnbindView();

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

    /**
     * 获取等待对话框实例，可以重写这个方法以实现自己的等待对话框
     *
     * @return
     */
    protected WaitingDialog getWaitingDialogInstance() {
        return WaitingDialogImpl.newDialog(this);
    }

    @Override
    public void showWaitingDialog(String text, boolean cancelable) {
        if (mWaitingDialog == null) {
            mWaitingDialog = getWaitingDialogInstance();
        }
        if (mWaitingDialog.isShowing()) {
            mWaitingDialog.dismiss();
        }

        mWaitingDialog.setMessage(text).setCancelable(cancelable).show();
    }

    @Override
    public void showWaitingDialog(int stringRes, boolean cancelable) {
        showWaitingDialog(getString(stringRes), cancelable);
    }

    @Override
    public void dismissWaitingDialog() {
        try {
            if (mWaitingDialog != null && mWaitingDialog.isShowing()) {
                mWaitingDialog.dismiss();
            }
        } catch (Exception e) {
            //e.printStackTrace();
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
        if ((view instanceof EditText)) {

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

    /**
     * 隐藏状态栏和导航栏
     */
    protected void hideSystemUI() {

        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    /**
     * 显示状态栏和导航栏
     */
    protected void showSystemUI() {
        // Shows the system bars by removing all the flags
        // except for the ones that make the content appear under the system bars.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
