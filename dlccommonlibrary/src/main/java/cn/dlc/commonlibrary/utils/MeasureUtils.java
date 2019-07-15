package cn.dlc.commonlibrary.utils;

import android.view.View;

public class MeasureUtils {

    /**
     * 自定义控件测量尺寸工具类，工具测量模式和提供尺寸，来测量出合适的尺寸
     *
     * @param measureSpec，有三种模式：UNSPECIFIED(不确定)、AT_MOST(至多)、EXACTLY(精确)
     * @param contentSize
     * @return 测量结果
     */
    public static int getMeasurement(int measureSpec, int contentSize) {
        int specMode = View.MeasureSpec.getMode(measureSpec);// 获取测量模式
        int specSize = View.MeasureSpec.getSize(measureSpec);// 根据测量规格获取尺寸
        int resultSize = 0;
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                //不确定
                resultSize = contentSize;
                break;
            case View.MeasureSpec.AT_MOST:
                //至多
                resultSize = Math.min(contentSize, specSize);
                break;
            case View.MeasureSpec.EXACTLY:
                //精确
                resultSize = specSize;
                break;
        }

        return resultSize;
    }
}
