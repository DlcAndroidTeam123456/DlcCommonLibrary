package cn.dlc.commonlibrary.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.TextView;
import cn.dlc.commonlibrary.R;

/**
 * 等待对话框
 */
public class WaitingDialog extends Dialog {

    private final TextView mTvMessage;

    public WaitingDialog(@NonNull Context context) {
        super(context, R.style.WaitingDialog);

        setContentView(R.layout.dialog_waiting);
        mTvMessage = (TextView) findViewById(R.id.tv_message);
    }

    public static WaitingDialog newDialog(Context context) {
        return new WaitingDialog(context);
    }

    /**
     * 设置对话框文本
     *
     * @param resId
     * @return
     */
    public WaitingDialog setMessage(@StringRes int resId) {
        mTvMessage.setText(resId);
        return this;
    }

    /**
     * 设置对话框文本
     *
     * @param message
     * @return
     */
    public WaitingDialog setMessage(CharSequence message) {
        mTvMessage.setText(message);
        return this;
    }
}
