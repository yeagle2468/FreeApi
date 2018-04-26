package com.yeagle.freeapi.base;

import android.os.Bundle;

import com.yeagle.freeapi.R;

import cn.yeagle.common.base.BaseActivity;
import cn.yeagle.common.utils.StatusBarUtil;

/**
 * Created by yeagle on 2018/4/13.
 */
public abstract class FreeApiActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        StatusBarUtil.statusBarLightMode(this);
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected int getTitleId() {
        return R.id.title_name_tv;
    }

    @Override
    protected int getBackTvId() {
        return R.id.back_tv;
    }

    @Override
    protected int getBackViewId() {
        return R.id.title_back_rl;
    }
}
