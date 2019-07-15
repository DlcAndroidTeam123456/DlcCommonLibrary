package cn.dlc.commonlibrary.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;

/**
 * 常用的时间格式
 * https://developer.android.com/reference/java/text/SimpleDateFormat.html
 *
 * yyyy-MM-dd 1969-12-31
 * yyyy-MM-dd 1970-01-01
 * yyyy-MM-dd HH:mm 1969-12-31 16:00
 * yyyy-MM-dd HH:mm 1970-01-01 00:00
 * yyyy-MM-dd HH:mmZ 1969-12-31 16:00-0800
 * yyyy-MM-dd HH:mmZ 1970-01-01 00:00+0000
 * yyyy-MM-dd HH:mm:ss.SSSZ 1969-12-31 16:00:00.000-0800
 * yyyy-MM-dd HH:mm:ss.SSSZ 1970-01-01 00:00:00.000+0000
 * yyyy-MM-dd'T'HH:mm:ss.SSSZ 1969-12-31T16:00:00.000-0800
 * yyyy-MM-dd'T'HH:mm:ss.SSSZ 1970-01-01T00:00:00.000+0000
 */

@SuppressLint("SimpleDateFormat")
public class TimeFormats {

    /**
     * 1969-12-31
     */
    public static final SimpleDateFormat FORMAT_1 = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 1969-12-31 16:00
     */
    public static final SimpleDateFormat FORMAT_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 16:00:00
     */
    public static final SimpleDateFormat FORMAT_3 = new SimpleDateFormat("HH:mm:ss");
    /**
     * 1969.12.31
     */
    public static final SimpleDateFormat FORMAT_4 = new SimpleDateFormat("yyyy.MM.dd");

    /**
     * 2017-06-10 11:45:56
     */
    public static final SimpleDateFormat FORMAT_5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 16:00   （时:分）
     */
    public static final SimpleDateFormat FORMAT_6 = new SimpleDateFormat("HH:mm");

    /**
     * 45:56   (分:秒)
     */
    public static final SimpleDateFormat FORMAT_7 = new SimpleDateFormat("mm:ss");

    /**
     * 1969-12
     */
    public static final SimpleDateFormat FORMAT_8 = new SimpleDateFormat("yyyy-MM");

    /**
     * 1969.12
     */
    public static final SimpleDateFormat FORMAT_9 = new SimpleDateFormat("yyyyy.MM");

    /**
     * 12-31
     */
    public static final SimpleDateFormat FORMAT_10 = new SimpleDateFormat("MM-dd");

    /**
     * 12.31
     */
    public static final SimpleDateFormat FORMAT_11 = new SimpleDateFormat("MM.dd");

    /**
     * 2017.06.10 11:45
     */
    public static final SimpleDateFormat FORMAT_12 = new SimpleDateFormat("yyyy.MM.dd HH:mm");


}
