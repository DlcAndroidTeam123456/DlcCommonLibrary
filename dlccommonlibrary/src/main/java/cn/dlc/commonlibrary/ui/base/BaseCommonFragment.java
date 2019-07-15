package cn.dlc.commonlibrary.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.dlc.commonlibrary.ui.base.mvp.UiView;
import cn.dlc.commonlibrary.utils.BindEventBus;
import me.yokeyword.fragmentation.ISupportFragment;
import org.greenrobot.eventbus.EventBus;

/**
 * 基础Fragment，建议继承此类再写一个BaseFragment
 */
public abstract class BaseCommonFragment extends BaseFragmentationFragment
    implements UiView, ISupportFragment {

    protected Activity mActivity;
    private Unbinder mUnBinder;

    /**
     * 设置视图资源id
     *
     * @return
     */
    protected abstract int getLayoutId();

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {

        View view = null;
        if (getLayoutId() != 0) {
            view = inflater.inflate(getLayoutId(), container, false);
            mUnBinder = ButterKnife.bind(this, view);
        }

        if (toRegisterEventBus()) {
            registerEventBus();
        }

        return view;
    }

    @Override
    public void onDestroyView() {

        unregisterEventBus();

        super.onDestroyView();

        // 在解绑控件前，先释放，避免 NullPointerException
        onDestroyUnbindView();

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
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
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在{@link #onDestroy()}生命周期中，运行在 黄油刀 {@link Unbinder#unbind()} 之前，
     * 因为{@link Unbinder#unbind()}运行后，注入的View会置null，
     * 某些特殊控件，如地图，需要明确的释放资源，可以重写此方法进行相关操作，避免出现 NullPointerException
     *
     * @see ButterKnife
     */
    protected void onDestroyUnbindView() {
        // mMapView.onDestroy(); 释放地图控件
    }

    @Override
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(getActivity(), cls));
    }

    @Override
    public void startActivityForResult(Class<? extends Activity> cls, int requestCode) {
        startActivityForResult(new Intent(getActivity(), cls), requestCode);
    }

    @Override
    public void finishActivity() {
        try {
            getActivityView().finishActivity();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void showToast(int resId) {
        try {
            getActivityView().showToast(resId);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void showToast(String text) {
        try {
            getActivityView().showToast(text);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void showOneToast(int resId) {
        try {
            getActivityView().showOneToast(resId);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void showOneToast(String text) {
        try {
            getActivityView().showOneToast(text);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void showWaitingDialog(String text, boolean cancelable) {
        try {
            getActivityView().showWaitingDialog(text, cancelable);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void showWaitingDialog(int stringRes, boolean cancelable) {
        try {
            getActivityView().showWaitingDialog(stringRes, cancelable);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void dismissWaitingDialog() {
        try {
            getActivityView().dismissWaitingDialog();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    @Override
    public boolean isActivityStarted() {
        try {
            return getActivityView().isActivityStarted();
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isActivityResumed() {
        try {
            return getActivityView().isActivityResumed();
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    private UiView getActivityView() {
        return (UiView) mActivity;
    }
}
