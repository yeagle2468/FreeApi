package com.yeagle.freeapi.main.module;

import com.yeagle.freeapi.base.ApiPagePresenter;
import com.yeagle.freeapi.base.BasePagePresenter;
import com.yeagle.freeapi.home.fragment.BeautyPicFragment;
import com.yeagle.freeapi.home.fragment.HomeFragment;
import com.yeagle.freeapi.home.fragment.PersonSignFragment;
import com.yeagle.freeapi.novel.NovelListFragment;

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

    @Binds
    abstract BasePagePresenter mainPresenter(ApiPagePresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract BeautyPicFragment beautyPicFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract PersonSignFragment personSignFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract NovelListFragment novelListFragment();
}
