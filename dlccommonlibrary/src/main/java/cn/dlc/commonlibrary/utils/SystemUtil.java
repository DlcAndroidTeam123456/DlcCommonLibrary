package cn.dlc.commonlibrary.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.licheedev.myutils.LogPlus;
import java.util.Iterator;
import java.util.List;

/**
 * 系统工具类
 */
public class SystemUtil {

    /**
     * 判断是否为主进程
     *
     * @param context
     * @return
     */
    public static boolean isMainProcess(Context context) {

        LogPlus.i("判断是否为主进程");

        ActivityManager activityManager =
            (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        String packageName = context.getPackageName();

        int myPid = android.os.Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> processInfos =
            activityManager.getRunningAppProcesses();
        if (processInfos == null) {
            LogPlus.i("isMainProcess get getRunningAppProcesses null");
            List<ActivityManager.RunningServiceInfo> serviceInfos =
                activityManager.getRunningServices(Integer.MAX_VALUE);

            if (serviceInfos == null) {
                LogPlus.i("isMainProcess get getRunningServices null");
                return false;
            } else {

                Iterator<ActivityManager.RunningServiceInfo> iterator = serviceInfos.iterator();

                ActivityManager.RunningServiceInfo serviceInfo;

                do {
                    if (!iterator.hasNext()) {
                        return false;
                    }
                    serviceInfo = iterator.next();
                } while (serviceInfo.pid != myPid || !packageName.equals(
                    serviceInfo.service.getPackageName()));
                return true;
            }
        } else {
            Iterator<ActivityManager.RunningAppProcessInfo> iterator = processInfos.iterator();

            ActivityManager.RunningAppProcessInfo processInfo;
            do {
                if (!iterator.hasNext()) {
                    return false;
                }

                processInfo = iterator.next();
            } while (processInfo.pid != myPid || !packageName.equals(processInfo.processName));

            return true;
        }
    }

    /**
     * 获取系统版本名
     *
     * @param context
     * @return 版本名
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0";
    }

    /**
     * 获取系统版本号
     *
     * @param context
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }
}
