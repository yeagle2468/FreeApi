package cn.yeagle.common.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by yeagle on 2018/1/31.
 */

public class HttpUtils {
    /**
     * 根据code来得到错误信息
     * @param context
     * @param preludeStr
     * @param errorCode
     * @return
     */
    public static String getErrorMsg(Context context, String preludeStr, int errorCode) {
        String packageName = context.getApplicationInfo().packageName;
        Resources res = context.getResources();

        StringBuffer sb = new StringBuffer(preludeStr);
        sb.append(errorCode);

        final int resId = res.getIdentifier(sb.toString(), "string", packageName);
        if (resId > 0) {
            return res.getString(resId);
        }

        return null;
    }

}
