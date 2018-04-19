package cn.yeagle.common.utils;


import android.util.Log;

import cn.yeagle.common.BuildConfig;

public class LogUtils {

	private static final boolean IS_LOG = BuildConfig.DEBUG;
    private static final String TAG = "LogUtils";

    public static void d(String msg) {
        if (IS_LOG) {
            Log.d(TAG, msg);
        }
    }

	public static void d(String tag, String msg) {
        if (IS_LOG) {
            Log.d(tag, msg);
        }
	}

	public static void i(String tag, String msg) {
        if (IS_LOG) {
            Log.i(tag, msg);
        }
	}

	public static void e(String tag, String msg) {
        if (IS_LOG) {
            Log.e( tag, msg);
        }
	}

    public static void e(String msg) {
        if (IS_LOG) {
            Log.e(TAG, msg);
        }
    }
	
	public static void w(String tag, String msg) {
        if (IS_LOG) {
            Log.w(tag, msg);
        }
	}

    public static void w(String tag, Exception e) {
        if (IS_LOG) {
            Log.w(tag,e);
        }
    }
	public static void w(String tag, String msg, Exception e) {
        if (IS_LOG) {
            Log.w(tag, msg,e);
        }
    }

    public static void v(String tag, String msg) {
        if (IS_LOG) {
            Log.v(tag, msg);
        }
    }
}
