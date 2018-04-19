package cn.yeagle.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * 系统属性工具类
 */
public class SysInfoUtil {
    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }


    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
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
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    public static String getIMEI(Context ctx) {
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
            if (tm != null) {
                imei = tm.getDeviceId();
            }
        } catch (Exception e) {
//            System.err.println("下列这个SecurityException权限异常出现在获取手机IMEI码过程中，获取不到也不会影响程序运行，可忽视");
//            e.printStackTrace();
        }
        return imei;
    }

    /**
     * 获取手机分辨率
     *
     * @param activity 注意参数是activity
     * @return 形如 720*1080的字符串
     */
    public static String getDisplayMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        return widthPixels + "*" + heightPixels;
    }


    /**
     * 获取手机分辨率
     *
     * @param context
     * @return 形如 720*1080的字符串
     */
    public static String getDisplayMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        return screenWidth + "*" + screenHeight;
    }

    /**
     * 获得国家码
     *
     * @return
     */
    public static String getCountryCode() {
        return Locale.getDefault().getCountry();
    }

    /**
     * 获取android id
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获得版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 是否需要更新
     *
     * @param context
     * @param versionCodeNew 新的版本号
     * @return
     */
    public static boolean isNeedUpdate(Context context, int versionCodeNew) {
        int versionCodeOld = getVersionCode(context);
        return versionCodeNew > versionCodeOld;
    }

    /**
     * 获得版本名
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    public static String getMetaData(Context context, String name) {
        ApplicationInfo actInfo = null;
        try {
            actInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            String md = actInfo.metaData.getString(name);
            return md;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
