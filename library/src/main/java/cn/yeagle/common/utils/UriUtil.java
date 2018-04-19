package cn.yeagle.common.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 *
 */

public class UriUtil {
    public static Uri fromFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", file);
        else
            return Uri.fromFile(file);
    }
}
