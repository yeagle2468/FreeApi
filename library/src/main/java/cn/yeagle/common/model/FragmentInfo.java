package cn.yeagle.common.model;

import android.support.annotation.IntDef;
import android.support.v4.app.Fragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yeagle on 2018/3/1.
 */
public class FragmentInfo {
    public static final int ACTION_ADD = 0;
    public static final int ACTION_REPLACE = 1;

    public Fragment fragment;
    public String tag;
    public int id;

    @ACTION
    public int action;

    public FragmentInfo(Fragment fragment, String tag, @ACTION int action) {
        this.fragment = fragment;
        this.tag = tag;
        this.action = action;
    }

    public FragmentInfo(Fragment fragment, int id, String tag, @ACTION int action) {
        this.fragment = fragment;
        this.id = id;
        this.tag = tag;
        this.action  = action;
    }

    @IntDef({ACTION_ADD, ACTION_REPLACE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ACTION {
    }
}
