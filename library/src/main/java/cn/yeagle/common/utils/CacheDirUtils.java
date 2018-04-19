package cn.yeagle.common.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 */
public class CacheDirUtils {
    private static final String CAMERA = "camera/";

    /**
     * 主要用来存放临时的数据
     * @return 返回缓存总目录
     */
    public static File getCacheDir(Context context) {
        if (isSdCardValid()) {
            return context.getExternalCacheDir();
        }

        return context.getCacheDir();
    }

    public static File getCacheDir(Context context, String fileName) {
        return new File(getCacheDir(context), fileName);
    }

    public static File getCamemaDir(Context context) {
        return new File(getCacheDir(context), CAMERA);
    }

    /**
     * 这个主要用来存放比较久的数据
     * @param context
     * @return
     */
    public static File getFileDir(Context context) {
        if (isSdCardValid()) {
            return context.getExternalFilesDir(null);
        }

        return context.getFilesDir();
    }

    /**
     *
     * @param context
     * @param fileName
     * @return
     */
    public static File getFileDir(Context context, String fileName) {
        return new File(getFileDir(context), fileName);
    }

    /**
     * 这个不跟随应用卸载而删除，会导致占用的空间越来越大，慎用
     * @return
     */
    public static File getSdCardDir() {
        if (isSdCardValid()) {
            return Environment.getExternalStorageDirectory();
        }

        return null;
    }

    public static File getSdCardDir(String fileName) {
        return new File(getSdCardDir(), fileName);
    }

    public static boolean isSdCardValid() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
