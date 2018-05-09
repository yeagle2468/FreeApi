package com.yeagle.freeapi.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.yeagle.common.utils.LogUtils;

/**
 * Created by yeagle on 2018/5/2.
 */
public abstract class ApiRecyclerFragment extends BaseRecyclerFragment implements PageContract.View {
    protected static final String PATH = "path";
    protected static final String TYPE = "type";

    @Override
    protected void onRefresh() {
        super.onRefresh();
        loadData(true);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        loadData(false);
    }

    private void loadData(boolean refresh) {
        Bundle bundle = getArguments();
        BasePagePresenter pagePresenter = getBasePagePresenter();
        if  (pagePresenter == null) {
            LogUtils.e(TAG, "pagePresenter is null");
            return;
        }

        pagePresenter.loadData(bundle.getString(PATH), refresh, bundle.getInt(TYPE), getTypeToken());
    }

    @Override
    public void onData(List data, boolean refresh, String path) {
        if (refresh && !mLoadedFirstData)
            mLoadedFirstData = true; // 第一次加载完毕

        if (data == null || data.size() < getPageNum()) {
            hasLoadedAllData = true; // 当获取的数据小于请求的数据，就算它已经加载完毕
            LogUtils.e(TAG, "hasLoadedAllData is true");
        }
        onData(data, refresh);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getBasePagePresenter().takeView(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onComplete(String path) {
        mLoading = false;
        hideLoading();
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
//        LogUtils.e(TAG, "showLoading");
    }

    @Override
    public void hideLoading() {
        if (mLoadingView != null)
            mLoadingView.setVisibility(View.GONE);
//        LogUtils.e(TAG, "hideLoading:" + mLoadingView + "::" + this);
    }

    protected abstract BasePagePresenter getBasePagePresenter();
    protected abstract TypeToken getTypeToken();

    protected void onData(List data, boolean refresh) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getBasePagePresenter().dropView();
    }
}
