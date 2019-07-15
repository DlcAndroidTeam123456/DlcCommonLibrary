package cn.dlc.commonlibrary.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;
import android.widget.TextView;
import java.util.Locale;

/**
 * Resources工具类
 */
public class ResUtil {

    private static Resources sResources;
    private static Context sContext;

    /**
     * 初始化Resources工具类
     *
     * @param context
     */
    public static void init(Context context) {
        sContext = context.getApplicationContext();
        sResources = context.getResources();
    }

    /**
     * 获取字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        checkContext();
        return sResources.getString(resId);
    }

    /**
     * 获取字符串
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 获取带格式的字符串
     *
     * @param resId
     * @param formatArgs
     * @return
     */
    public static String getString(int resId, Object... formatArgs) {
        checkContext();
        return sResources.getString(resId, formatArgs);
    }

    /**
     * 获取带格式化的字符串
     *
     * @param context
     * @param resId
     * @param formatArgs
     * @return
     */
    public static String getString(Context context, int resId, Object... formatArgs) {
        return context.getString(resId, formatArgs);
    }

    /**
     * 获取带html格式的字符串
     *
     * @param resId
     * @param formatArgs
     * @return
     */
    public static Spanned fromHtml(int resId, Object... formatArgs) {
        checkContext();
        String text = sResources.getString(resId, formatArgs);
        return Html.fromHtml(text);
    }

    /**
     * 获取带html格式的字符串
     *
     * @param context
     * @param resId
     * @param formatArgs
     * @return
     */
    public static Spanned fromHtml(Context context, int resId, Object... formatArgs) {
        String text = context.getResources().getString(resId, formatArgs);
        return Html.fromHtml(text);
    }

    /**
     * 直接设置带html格式的字符串到TextView里面
     *
     * @param textView
     * @param resId
     * @param formatArgs
     */
    public static void setHtml(TextView textView, int resId, Object... formatArgs) {
        String string = textView.getResources().getString(resId, formatArgs);
        textView.setText(Html.fromHtml(string));
    }

    /**
     * 获取颜色
     *
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        checkContext();
        return ContextCompat.getColor(sContext, resId);
    }

    /**
     * 获取颜色Selector
     *
     * @param resId
     * @return
     */
    public static ColorStateList getColorStateList(int resId) {
        checkContext();
        return ContextCompat.getColorStateList(sContext, resId);
    }

    /**
     * 获取颜色
     *
     * @param context
     * @param resId
     * @return
     */
    public static int getColor(Context context, int resId) {
        return ContextCompat.getColor(context, resId);
    }

    /**
     * 获取颜色Selector
     *
     * @param context
     * @param resId
     * @return
     */
    public static ColorStateList getColorStateList(Context context, int resId) {
        return ContextCompat.getColorStateList(context, resId);
    }

    /**
     * 获取尺寸资源对应的像素
     *
     * @param resId
     * @return
     */
    public static float getPx(int resId) {
        checkContext();
        return getPx(sResources, resId);
    }

    /**
     * 获取尺寸资源对应的像素
     *
     * @param resources
     * @param resId
     * @return
     */
    public static float getPx(Resources resources, int resId) {
        return resources.getDimension(resId);
    }

    /**
     * 获取尺寸资源对应的像素
     *
     * @param context
     * @param resId
     * @return
     */
    public static float getPx(Context context, int resId) {
        return getPx(context.getResources(), resId);
    }

    /**
     * 应用TextView字体大小
     *
     * @param textView
     * @param resId
     */
    public static void applyTextSize(TextView textView, int resId) {
        float pixel = getPx(textView.getResources(), resId);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, pixel);
    }

    /**
     * 获取Drawable资源
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(int resId) {
        checkContext();
        return sContext.getResources().getDrawable(resId);
    }

    /**
     * 获取Drawable资源
     *
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }

    /**
     * 获取默认的Resources对象
     *
     * @return
     */
    public static Resources getResources() {
        checkContext();
        return sResources;
    }

    public static Locale getLocale() {
        return sResources.getConfiguration().locale;
    }

    /**
     * 检查是否有初始化过Content
     */
    private static void checkContext() {
        if (sContext == null) {
            throw new NullPointerException("Context为null，请先调用ResUtil.init(context)");
        }
    }
}
