package com.yeagle.freeapi.base;

import android.os.Bundle;
import android.view.View;

import cn.yeagle.common.base.BaseFragment;

/**
 * Created by yeagle on 2018/5/2.
 */
public abstract class LazyFragment extends BaseFragment {
    /**
     * 界面是否已经初始化
     */
    private boolean isInit;

    /**
     * 是否已经加载调用了懒加载方法lazyLoad
     */
    protected boolean isLoad;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initViews();
        this.isInit = true;
        this.isCanLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //对用户是否可见时
        this.isCanLoadData();
    }

    /**
     * 判断是否对用户可见，并且已经初始化
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) { //没有初始化
            return;
        }

        if (getUserVisibleHint()) {
            //对用户可见，并且界面已经初始化，isInit为true
            if (!isLoad) {
                lazyLoad();
                isLoad = true;
            }
            onVisible();
        } else {
            onInVisible();
        }
    }

    /**
     * 懒加载中，开始加载数据（只在初次显示出来的时候被调用）
     */
    protected void lazyLoad() {
    }

    /**
     * ViewPager切换回来后调用
     */
    protected void onVisible() {
    }

    /**
     * ViewPager切换出去后调用
     */
    protected void onInVisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }
}
