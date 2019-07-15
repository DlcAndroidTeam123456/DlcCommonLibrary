package cn.dlc.commonlibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

/**
 * 屏幕尺寸工具类，需要调用{@link ScreenUtil#init(android.content.Context)}进行初始化
 */

public class ScreenUtil {

    private static final Point sRealSize = new Point();

    private static int sStatusHeight = 0;

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {

        Resources resources = context.getResources();

        updateRealSize(context);

        sStatusHeight = getStatusHeight(resources);
    }

    /**
     * 获取状态栏高度
     *
     * @param resources
     * @return
     */
    public static int getStatusHeight(Resources resources) {
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = resources.getDimensionPixelSize(resourceId);
        return statusBarHeight;
    }

    private static void updateRealSize(Context context) {

        WindowManager windowManager =
            (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();

        try {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.HONEYCOMB_MR2) {
                int width = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                int height = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
                sRealSize.set(width, height);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {

                Display.class.getMethod("getRealSize", Point.class).invoke(display, sRealSize);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(sRealSize);
            } else {
                display.getSize(sRealSize);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            display.getSize(sRealSize);
        }

        // 判断宽度是不是比高度长
        if (sRealSize.x > sRealSize.y) {
            sRealSize.set(sRealSize.y, sRealSize.x);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusHeight() {
        checkInit();
        return sStatusHeight;
    }

    /**
     * 获取屏幕真正的尺寸
     *
     * @return
     */
    public static Point getRealSize() {
        checkInit();
        return sRealSize;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getRealWidth() {
        checkInit();
        return sRealSize.x;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getRealHeight() {
        checkInit();
        return sRealSize.y;
    }

    /**
     * 获取宽高比
     *
     * @return
     */
    public static float getXYScale() {
        checkInit();
        return sRealSize.x / (float) sRealSize.y;
    }

    /**
     * 获取进行宽高比缩放后的宽度
     *
     * @param height
     * @return
     */
    public static int getScaleWidth(int height) {
        checkInit();
        return (int) (height * sRealSize.x / (float) sRealSize.y);
    }

    /**
     * 获取进行宽高比缩放后的高度
     *
     * @param width
     * @return
     */
    public static int getScaleHeight(int width) {
        checkInit();
        return (int) (width * sRealSize.y / (float) sRealSize.x);
    }

    /**
     * 检查是否有初始化过Content
     */
    private static void checkInit() {
        if (sStatusHeight == 0) {
            throw new IllegalStateException("ScreenUtil未初始化，请先调用ScreenUtil.init(context)进行初始化");
        }
    }
}
