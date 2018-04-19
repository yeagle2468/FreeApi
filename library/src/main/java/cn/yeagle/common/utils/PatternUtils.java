package cn.yeagle.common.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yeagle on 2018/2/9.
 */

public class PatternUtils {
    public static boolean isMobileNumber(String mobiles) {
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        }
        if (mobiles.length() < 4 || mobiles.length() > 20) {
            return false;
        }
        try {
            Pattern p = Pattern.compile("(13[0-9]|14[57]|15[012356789]|18[0-9])\\d{8}");
            Matcher m = p.matcher(mobiles);

            Pattern p2 = Pattern.compile("(170[0-9])\\d{7}");
            Matcher m2 = p2.matcher(mobiles);

            Pattern p3 = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
            Matcher m3 = p3.matcher(mobiles);

            Pattern p4 = Pattern.compile("[0-9]{1,}");
            Matcher m4 = p4.matcher(mobiles);

            return m.matches() || m2.matches() || m3.matches() || m4.matches();
        } catch (Exception e) {
            return false;
        }

    }
}
