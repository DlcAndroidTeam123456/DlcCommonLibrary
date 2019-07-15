package cn.dlc.commonlibrary.utils;

import java.util.Calendar;

/**
 * 时间工具类
 */
public class TimeUtil {

    /**
     * 一天的时间长度（毫秒）
     */
    public static final long ONE_DAY = 24 * 60 * 60 * 1000L;
    /**
     * 一天的时间长度（秒）
     */
    public static final long ONE_DAY_SECOND = 24 * 60 * 60L;
    /**
     * 半天的时间长度（毫秒）
     */
    public static final long HALF_DAY = ONE_DAY / 2;
    /**
     * 半天的时间长度（秒）
     */
    public static final long HALF_DAY_SECOND = HALF_DAY / 1000L;
    /**
     * 一个小时的时间长度（毫秒）
     */
    public static final long ONE_HOUR = 3600 * 1000;
    /**
     * 一个小时的时间长度（秒）
     */
    public static final long ONE_HOUR_SECOND = 3600;
    /**
     * 一分钟的时间长度（毫秒）
     */
    public static final long ONE_MINUTE = 60 * 1000;
    /**
     * 一分钟的时间长度，秒
     */
    public static final long ONE_MINUTE_SECOND = 60;

    /**
     * 一周的时间长度（毫秒）
     */
    public static final long ONE_WEEK = 7 * 24 * 60 * 60 * 1000L;

    /**
     * 一周的时间长度（秒）
     */
    public static final long ONE_WEEK_SECOND = 7 * 24 * 60 * 60;

    /**
     * 当前时区与0时区的时间偏移（毫秒，负数表示更早到0点，-28800000为东8区的偏移）
     */
    public static final long TIME_OFFSET = getUnixTime(1970, 1, 1, 0);
    /**
     * 当前时区与0时区的时间偏移（秒，负数表示更早到0点，-28800为东8区的偏移）
     */
    public static final long TIME_OFFSET_SECOND = TIME_OFFSET / 1000;

    /**
     * 转换Unix时间戳
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @param minute 分，0~59
     * @param second 秒，0~59
     * @param millisecond 毫秒，0~999
     * @return
     */
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute,
        int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar;
    }

    /**
     * 转换Unix时间戳
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @param minute 分，0~59
     * @param second 秒，0~59
     * @return
     */
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute,
        int second) {
        return getCalendar(year, month, day, hour, minute, second, 0);
    }

    /**
     * 转换Unix时间戳
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @param minute 分，0~59
     * @return
     */
    public static Calendar getCalendar(int year, int month, int day, int hour, int minute) {
        return getCalendar(year, month, day, hour, minute, 0, 0);
    }

    /**
     * 转换Unix时间戳
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     */
    public static Calendar getCalendar(int year, int month, int day, int hour) {
        return getCalendar(year, month, day, hour, 0, 0, 0);
    }

    /**
     * 转换Unix时间戳
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     */
    public static Calendar getCalendar(int year, int month, int day) {
        return getCalendar(year, month, day, 0, 0, 0, 0);
    }

    /**
     * 获取Unix时间戳
     *
     * @param timeInMillis unix时间戳，毫秒
     * @return
     */
    public static Calendar getCalendar(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

    /**
     * 获取精确到日的时间，时分秒毫秒会被清零
     *
     * @param calendar
     * @return
     */
    public static Calendar getDateCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取精确到日的时间，时分秒毫秒会被清零
     *
     * @param timeMillis
     * @return
     */
    public static Calendar getDateCalendar(long timeMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 转换Unix时间戳(毫秒)
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @param minute 分，0~59
     * @param second 秒，0~59
     * @param millisecond 毫秒，0~999
     * @return
     */
    public static long getUnixTime(int year, int month, int day, int hour, int minute, int second,
        int millisecond) {
        return getCalendar(year, month, day, hour, minute, second, millisecond).getTimeInMillis();
    }

    /**
     * 转换Unix时间戳(毫秒)
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @param minute 分，0~59
     * @param second 秒，0~59
     * @return
     */
    public static long getUnixTime(int year, int month, int day, int hour, int minute, int second) {
        return getCalendar(year, month, day, hour, minute, second, 0).getTimeInMillis();
    }

    /**
     * 转换Unix时间戳(毫秒)
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @param minute 分，0~59
     * @return
     */
    public static long getUnixTime(int year, int month, int day, int hour, int minute) {
        return getCalendar(year, month, day, hour, minute, 0, 0).getTimeInMillis();
    }

    /**
     * 转换Unix时间戳(毫秒)
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @return
     */
    public static long getUnixTime(int year, int month, int day, int hour) {
        return getCalendar(year, month, day, hour, 0, 0, 0).getTimeInMillis();
    }

    /**
     * 转换Unix时间戳(毫秒)
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @return
     */
    public static long getUnixTime(int year, int month, int day) {
        return getCalendar(year, month, day, 0, 0, 0, 0).getTimeInMillis();
    }

    /**
     * 转换Unix时间戳(秒)
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @param minute 分，0~59
     * @param second 秒，0~59
     * @return
     */
    public static long getUnixSecond(int year, int month, int day, int hour, int minute,
        int second) {
        return getCalendar(year, month, day, hour, minute, second, 0).getTimeInMillis() / 1000;
    }

    /**
     * 转换Unix时间戳(秒)
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     * @param minute 分，0~59
     * @return
     */
    public static long getUnixSecond(int year, int month, int day, int hour, int minute) {
        return getCalendar(year, month, day, hour, minute, 0, 0).getTimeInMillis() / 1000;
    }

    /**
     * 转换Unix时间戳(秒)
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     * @param hour 时，0~23
     */
    public static long getUnixSecond(int year, int month, int day, int hour) {
        return getCalendar(year, month, day, hour, 0, 0, 0).getTimeInMillis() / 1000;
    }

    /**
     * 转换Unix时间戳(秒)
     *
     * @param year 年
     * @param month 月，1~12
     * @param day 日
     */
    public static long getUnixSecond(int year, int month, int day) {
        return getCalendar(year, month, day, 0, 0, 0, 0).getTimeInMillis() / 1000;
    }
}
