package com.yeagle.freeapi.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.yeagle.freeapi.api.Api;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mPagePresenter.takeView(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initAdapter() {
        mRcView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcView.setAdapter(new BeautyPicAdapter(getContext(), new ArrayList<>()));
    }

    @Override
    protected void onData(Object object, boolean refresh) {
        super.onData(object, refresh);

        if (mAdapter == null) {
            mAdapter = new BeautyPicAdapter(getContext(), (List<BeautyInfo>) object);
        } else if (refresh) {
            mAdapter.setData((List<BeautyInfo>) object);
        } else {
            mAdapter.addData((List<BeautyInfo>) object);
        }
    }

    @Override
    protected BasePagePresenter getBasePagePresenter() {
        return mPagePresenter;
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public boolean useInject() {
        return true;
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
