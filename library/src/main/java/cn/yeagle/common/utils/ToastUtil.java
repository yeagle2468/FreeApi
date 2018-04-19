package cn.yeagle.common.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.widget.Toast;

import cn.yeagle.common.base.BaseApp;

/**
 *
 */
public class ToastUtil {

    private static Toast toast = null;

    private ToastUtil() {
    }

    public static void showToast(String str, int duration) {
        Toast toast = Toast.makeText(BaseApp.getContext(), str, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(String str) {
        showToast(str, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast，每次只显示一条Toast
     * 推荐使用这个，可以取消上一次Toast消息
     */
    @SuppressLint("ShowToast")
    public static void showToastOnly(String text) {
        if (toast == null) {
            toast = Toast.makeText(BaseApp.getContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
