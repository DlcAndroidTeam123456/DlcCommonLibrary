package cn.dlc.commonlibrary.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;

/**
 * 常用的时间格式
 * https://developer.android.com/reference/java/text/SimpleDateFormat.html
 *
 * yyyy-MM-dd 1969-12-31
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
public class TimeFormatsUtil {

    private static SimpleDateFormat instance;
    private static SimpleDateFormat instance2;
    private static SimpleDateFormat instance3;
    private static SimpleDateFormat instance4;
    private static SimpleDateFormat instance5;
    private static SimpleDateFormat instance6;
    private static SimpleDateFormat instance7;
    private static SimpleDateFormat instance8;
    private static SimpleDateFormat instance9;
    private static SimpleDateFormat instance10;
    private static SimpleDateFormat instance11;
    private static SimpleDateFormat instance12;

    public static String setTime1(long ctime) {
        if (instance == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance == null) {
                    instance = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                }
            }
        }
        return instance.format(ctime * 1000L);
    }

    public static String setTime2(long ctime) {
        if (instance2 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance2 == null) {
                    instance2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                }
            }
        }
        return instance2.format(ctime * 1000L);
    }
    
    public static String setTime3(long ctime) {
        if (instance3 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance3 == null) {
                    instance3 = new SimpleDateFormat("yyyy-MM-dd");
                }
            }
        }
        return instance3.format(ctime * 1000L);
    }

    public static String setTime4(long ctime) {
        if (instance4 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance4 == null) {
                    instance4 = new SimpleDateFormat("yyyy-MM");
                }
            }
        }
        return instance4.format(ctime * 1000L);
    }

    public static String setTime5(long ctime) {
        if (instance5 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance5 == null) {
                    instance5 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                }
            }
        }
        return instance5.format(ctime * 1000L);
    }

    
    public static String setTime6(long ctime) {
        if (instance6 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance6 == null) {
                    instance6 = new SimpleDateFormat("yyyy.MM.dd HH:mm");
                }
            }
        }
        return instance6.format(ctime * 1000L);
    }

    public static String setTime7(long ctime) {
        if (instance7 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance7 == null) {
                    instance7 = new SimpleDateFormat("yyyy.MM.dd");
                }
            }
        }
        return instance7.format(ctime * 1000L);
    }

    public static String setTime8(long ctime) {
        if (instance8 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance8 == null) {
                    instance8 = new SimpleDateFormat("yyyy.MM");
                }
            }
        }
        return instance8.format(ctime * 1000L);
    }
    
    public static String setTime9(long ctime) {
        if (instance9 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance9 == null) {
                    instance9 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                }
            }
        }
        return instance9.format(ctime * 1000L);
    }

    public static String setTime10(long ctime) {
        if (instance10 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance10 == null) {
                    instance10 = new SimpleDateFormat("HH小时mm分ss秒 HH:mm");
                }
            }
        }
        return instance10.format(ctime * 1000L);
    } 
    
    public static String setTime11(long ctime) {
        if (instance11 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance11 == null) {
                    instance11 = new SimpleDateFormat("HH小时mm分ss秒");
                }
            }
        }
        return instance11.format(ctime * 1000L);
    } 
    
    public static String setTime12(long ctime) {
        if (instance12 == null) {
            synchronized (SimpleDateFormat.class) {
                if (instance12 == null) {
                    instance12 = new SimpleDateFormat("HH小时mm分");
                }
            }
        }
        return instance12.format(ctime * 1000L);
    }
}
