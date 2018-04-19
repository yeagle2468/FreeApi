package com.yeagle.freeapi.di;

import com.yeagle.freeapi.main.activity.MainActivity;
import com.yeagle.freeapi.main.module.MainModule;

import cn.yeagle.common.di.scope.ActivityScoped;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 */
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();
}
