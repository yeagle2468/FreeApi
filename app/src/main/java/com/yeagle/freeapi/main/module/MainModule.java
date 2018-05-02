package com.yeagle.freeapi.main.module;

import com.yeagle.freeapi.base.ApiPagePresenter;
import com.yeagle.freeapi.base.BasePagePresenter;
import com.yeagle.freeapi.home.fragment.HomeFragment;

import cn.yeagle.common.di.scope.ActivityScoped;
import cn.yeagle.common.di.scope.FragmentScoped;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by yeagle on 2018/4/17.
 */
@Module
public abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeFragment homeFragment();

//    @ActivityScoped
//    @Binds
//    abstract BasePagePresenter mainPresenter(ApiPagePresenter presenter);
}
