package cn.dlc.commonlibrary.ui.widget.status;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

public class StatusDelegate {

    private final int mStatusHeight;
    private boolean mFitStatusBar;

    public StatusDelegate(Context context) {
        this(context, false);
    }

    public StatusDelegate(Context context, boolean fitStatusBar) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        mStatusHeight = resources.getDimensionPixelSize(resourceId);

        mFitStatusBar = fitStatusBar;
    }

    public int getStatusHeight() {
        return mStatusHeight;
    }

    /**
     * 是否适配状态栏
     *
     * @return
     */
    public boolean isFitStatusBar() {
        return mFitStatusBar;
    }

    /**
     * 设置适配状态栏
     *
     * @param fitStatusBar
     */
    public void setFitStatusBar(boolean fitStatusBar) {
        mFitStatusBar = fitStatusBar;
    }

    /**
     * 可以设置透明状态栏
     *
     * @return
     */
    public boolean canTranslucentStatus() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * 是否应该去适配状态栏
     *
     * @return
     */
    public boolean toFitStatusBar() {
        return isFitStatusBar() & canTranslucentStatus();
    }
}
