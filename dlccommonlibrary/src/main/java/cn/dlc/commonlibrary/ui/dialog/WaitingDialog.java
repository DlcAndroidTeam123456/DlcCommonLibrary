package cn.dlc.commonlibrary.ui.dialog;

import android.support.annotation.StringRes;

/**
 * 等待对话框
 */
public interface WaitingDialog<D> {

    /**
     * 设置对话框文本
     *
     * @param resId
     * @return
     */
    WaitingDialog setMessage(@StringRes int resId);

    /**
     * 设置对话框文本
     *
     * @param message
     * @return
     */
    WaitingDialog setMessage(CharSequence message);

    /**
     * 设置隐藏Dialog
     *
     * @param flag
     * @return
     */
    WaitingDialog setCancelable(boolean flag);

    /**
     * 显示对话框
     */
    void show();

    /**
     * 是否正在显示
     *
     * @return
     */
    boolean isShowing();

    /**
     * 隐藏对话框
     */
    void dismiss();

    /**
     * 获取真正的对话框实例
     *
     * @return
     */
    D getDialog();
}
