package cn.dlc.commonlibrary.utils;

import android.app.Dialog;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 对话框工具类，可以修复自定义对话框，布局参数无效的问题
 */
public class DialogUtil {

    /**
     * 调整对话框布局，使填充布局的layout参数有效
     *
     * @param dialog
     * @param matchParentWidth
     * @param matchParentHeight
     */
    public static void adjustDialogLayout(Dialog dialog, boolean matchParentWidth,
        boolean matchParentHeight) {

        Window window = dialog.getWindow();

        // 必须调用此方法让DecorView初始化，否则下面的设置布局参数无效
        // dialog
        window.getDecorView();

        int w = matchParentWidth ? WindowManager.LayoutParams.MATCH_PARENT
            : WindowManager.LayoutParams.WRAP_CONTENT;

        int h = matchParentHeight ? WindowManager.LayoutParams.MATCH_PARENT
            : WindowManager.LayoutParams.WRAP_CONTENT;

        window.setLayout(w, h);
    }

    /**
     * 填充并返回Dialog视图，布局参数可用
     *
     * @param dialog
     * @param layoutResID
     * @return
     */
    public static View setContentView(Dialog dialog, @LayoutRes int layoutResID) {

        adjustDialogLayout(dialog, true, false);

        FrameLayout contentParent =
            (FrameLayout) dialog.getWindow().getDecorView().findViewById(Window.ID_ANDROID_CONTENT);

        View view = dialog.getLayoutInflater().inflate(layoutResID, contentParent, false);
        dialog.setContentView(view, view.getLayoutParams());

        return view;
    }

    @IntDef({
        Gravity.CENTER, Gravity.BOTTOM, Gravity.TOP, Gravity.CENTER_HORIZONTAL,
        Gravity.CENTER_VERTICAL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DialogGravity {
    }

    /**
     * 设置对话框位置
     *
     * @param dialog
     * @param gravity
     */
    public static void setGravity(Dialog dialog, @DialogGravity int gravity) {
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(gravity);
    }

    /**
     * 让对话框自动弹出输入法
     *
     * @param dialog
     */
    public static void setAutoShowIme(Dialog dialog) {
        dialog.getWindow()
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
}
