package com.yeagle.freeapi;

import com.yeagle.freeapi.di.DaggerAppComponent;

import cn.yeagle.common.base.BaseApp;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by yeagle on 2018/4/16.
 */
public class App extends BaseApp {
    @Override
    public AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
