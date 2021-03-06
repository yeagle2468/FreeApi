package com.yeagle.freeapi.home.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yeagle.freeapi.R;
import com.yeagle.freeapi.adapter.SimpleFragmentPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import cn.yeagle.common.base.BaseFragment;
import cn.yeagle.common.di.scope.ActivityScoped;

/**
 * Created by yeagle on 2018/4/18.
 */
@ActivityScoped
public class HomeFragment extends BaseFragment {

    private static final int TAB_NUMS = 3;

    @BindView(R.id.vp_home)
    ViewPager mViewPager;
    @BindView(R.id.tl_home)
    TabLayout mTabLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.title_name_tv)
    TextView mTitleNameView;

    @BindView(R.id.title_back_rl)
    View mBackView;

    private SimpleFragmentPagerAdapter mPageAdapter;
    private Fragment[] mFragments = new Fragment[TAB_NUMS];

    @Inject
    public HomeFragment() {
    }

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

        if (mPageAdapter == null) {
            mFragments[0] = new BeautyPicFragment();
            mFragments[1] = new PersonSignFragment();
            mFragments[2] = new SatinFragment();
        }

        String titles[] = getResources().getStringArray(R.array.home_titles);//{"美图"};
        mPageAdapter = new SimpleFragmentPagerAdapter(getChildFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(mPageAdapter);

        mTitleNameView.setText(R.string.home);
        mBackView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
