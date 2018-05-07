package com.yeagle.freeapi.main.activity;

import android.os.Bundle;
import android.support.annotation.IntDef;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.FreeApiActivity;
import com.yeagle.freeapi.home.fragment.HomeFragment;
import com.yeagle.freeapi.novel.NovelListFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends FreeApiActivity {
    private static final String POSITION = "pos";
    private static final int HOME_POS    = 0;
    private static final int NOVEL_POS   = 1;

    @IntDef({HOME_POS, NOVEL_POS})
    public @interface Pos {
    }

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar navigationBar;
    @Inject
    HomeFragment mHomeFragment;
    @Inject
    NovelListFragment mNovelListFragment;

    private int mPosition;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle bundle) {
        if (bundle != null)
            mPosition = bundle.getInt(POSITION);

        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        navigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home, R.string.home)
                .setActiveColorResource(R.color.colorAccent)).setInActiveColor(R.color.main_black)
                .addItem(new BottomNavigationItem(R.drawable.ic_wether, R.string.weather)
                .setActiveColorResource(R.color.colorAccent)).setInActiveColor(R.color.main_black)
                .addItem(new BottomNavigationItem(R.drawable.ic_mine, R.string.mine)
                .setActiveColorResource(R.color.colorAccent)).setInActiveColor(R.color.main_black)
                .setFirstSelectedPosition(0)
                .initialise();

        navigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@Pos int position) {
                if (position == mPosition)
                    return;

                setPosition(position);
                mPosition = position;
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });

        setPosition(mPosition);
//        replaceFragment(mHomeFragment, R.id.content_frame);
    }

    private void setPosition(@Pos int pos) {
        switch (pos) {
            case HOME_POS:
                replaceFragment(mHomeFragment, R.id.content_frame);
                break;
            case NOVEL_POS:
                replaceFragment(mNovelListFragment, R.id.content_frame);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, mPosition);
    }

    @Override
    protected boolean isBackViewVisible() {
        return false;
    }
}
