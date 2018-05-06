package com.yeagle.freeapi.home.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.yeagle.freeapi.base.ApiRecyclerFragment;
import com.yeagle.freeapi.base.BasePagePresenter;
import com.yeagle.freeapi.home.model.SatinInfo;

import javax.inject.Inject;

/**
 * Created by yeagle on 2018/4/18.
 */
public class SatinFragment extends ApiRecyclerFragment {
    @Inject
    BasePagePresenter mPagePresenter;

    @Override
    protected void onRefresh() {
        super.onRefresh();
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return null;
    }

    @Override
    protected BasePagePresenter getBasePagePresenter() {
        return mPagePresenter;
    }

    @Override
    protected TypeToken getTypeToken() {
        return new TypeToken<SatinInfo>(){};
    }
}
