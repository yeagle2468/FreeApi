package com.yeagle.freeapi.home.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.yeagle.freeapi.R;

import butterknife.BindView;
import cn.yeagle.common.base.BaseFragment;

/**
 * Created by yeagle on 2018/4/18.
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.vp_home)
    ViewPager mViewPager;
    @BindView(R.id.tl_home)
    TabLayout mTabLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
