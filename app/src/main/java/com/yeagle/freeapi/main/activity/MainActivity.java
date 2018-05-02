package com.yeagle.freeapi.main.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.FreeApiActivity;
import com.yeagle.freeapi.home.fragment.HomeFragment;

import butterknife.BindView;

public class MainActivity extends FreeApiActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar navigationBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle bundle) {
        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        navigationBar.addItem(new BottomNavigationItem(R.drawable.ic_home, R.string.home)
                .setActiveColorResource(R.color.colorAccent)).setInActiveColor(R.color.main_black)
                .addItem(new BottomNavigationItem(R.drawable.ic_wether, R.string.weather)
                .setActiveColorResource(R.color.colorAccent)).setInActiveColor(R.color.main_black)
                .addItem(new BottomNavigationItem(R.drawable.ic_mine, R.string.mine)
                .setActiveColorResource(R.color.colorAccent)).setInActiveColor(R.color.main_black)
                .setFirstSelectedPosition(0)
                .initialise();

        replaceFragment(new HomeFragment(), R.id.content_frame);
    }

    @Override
    protected boolean isBackViewVisible() {
        return false;
    }
}
