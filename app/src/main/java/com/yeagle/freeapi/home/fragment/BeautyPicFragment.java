package com.yeagle.freeapi.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.yeagle.freeapi.network.api.Api;
import com.yeagle.freeapi.base.ApiRecyclerFragment;
import com.yeagle.freeapi.base.BasePagePresenter;
import com.yeagle.freeapi.home.adapter.BeautyPicAdapter;
import com.yeagle.freeapi.home.model.BeautyInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.yeagle.common.di.scope.ActivityScoped;

/**
 * Created by yeagle on 2018/5/2.
 * 美图
 */
@ActivityScoped
public class BeautyPicFragment extends ApiRecyclerFragment {
    private BeautyPicAdapter mAdapter;

    @Inject
    BasePagePresenter mPagePresenter;

    @Inject
    public BeautyPicFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(PATH, Api.BEAUTY_PATH);
        setArguments(bundle);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new BeautyPicAdapter(getContext(), new ArrayList<>());
        return mAdapter;
    }

    @Override
    protected void onData(List data, boolean refresh) {
        super.onData(data, refresh);

        if (mAdapter == null) {
            mAdapter = new BeautyPicAdapter(getContext(), (List<BeautyInfo>) data);
            mRcView.setAdapter(mAdapter);
        } else if (refresh) {
            mAdapter.setData((List<BeautyInfo>) data);
        } else {
            mAdapter.addData((List<BeautyInfo>) data);
//            LogUtils.e(TAG, "adapter data size:" + mAdapter.getItemCount() + this);
        }
    }

    @Override
    protected boolean scrollShowPicture() {
        return true;
    }

    @Override
    protected BasePagePresenter getBasePagePresenter() {
        return mPagePresenter;
    }

    @Override
    protected TypeToken getTypeToken() {
        return new TypeToken<List<BeautyInfo>>(){};
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPagePresenter.dropView();
    }
}
