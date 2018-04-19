package cn.yeagle.common.utils;

import android.os.Build;

import java.io.IOException;
import java.lang.reflect.Method;

import cn.yeagle.common.utils.BuildProperties;

/**
 * 手机系统检测
 */
public class PhoneSysUtil {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    private static boolean isMIUI;
    private static boolean hasCheckMIUI;

    /**
     * 很多地方用到，内存缓存
     * @return
     */
    public static boolean isMIUI() {
        if (hasCheckMIUI)
            return isMIUI;

        try {
            final BuildProperties prop = BuildProperties.newInstance();
            hasCheckMIUI = true;
            isMIUI = prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            hasCheckMIUI = true;
            isMIUI = false;
        }

        return isMIUI;
    }

    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }
}
