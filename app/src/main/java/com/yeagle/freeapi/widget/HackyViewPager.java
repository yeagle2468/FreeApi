package com.yeagle.freeapi.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * There are some ViewGroups (ones that utilize onInterceptTouchEvent) that throw exceptions when a PhotoView is placed within them,
 * most notably ViewPager and DrawerLayout.
 * This is a framework issue that has not been resolved.
 * Any ViewGroup which uses onInterceptTouchEvent will also need to be extended and exceptions caught.
 * 系统的bug，只能这样解决
 */
public class HackyViewPager extends ViewPager {
    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
