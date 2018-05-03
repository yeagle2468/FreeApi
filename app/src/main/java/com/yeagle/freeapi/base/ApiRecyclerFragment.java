package com.yeagle.freeapi.base;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.yeagle.common.utils.LogUtils;

/**
 * Created by yeagle on 2018/5/2.
 */
public abstract class ApiRecyclerFragment extends BaseRecyclerFragment implements PageContract.View {
    protected static final String PATH = "path";
    protected static final String TYPE = "type";

//    @Inject
//    BasePagePresenter mPagePresenter;

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

        pagePresenter.loadData(bundle.getString(PATH), refresh, bundle.getInt(TYPE), getTypeToken()); // getTypeToken() new TypeToken<List<BeautyInfo>>(){})
//        pagePresenter.loadData(bundle.getString(PATH), refresh, bundle.getInt(TYPE), new ErrorHandleSubscriber(pagePresenter.getRxErrorHandler()) {
//            @Override
//            public void onNext(Object o) {
//                onData(o, refresh);
//            }
//
//            @Override
//            public void onComplete() {
//                mLoading = false;
//            }
//        });
    }

    @Override
    public void onData(List data, boolean refresh, String path) {
        if (refresh && !mLoadedFirstData)
            mLoadedFirstData = true; // 第一次加载完毕

        if (data == null || data.size() < getPageNum()) {
            hasLoadedAllData = true; // 当获取的数据小于请求的数据，就算它已经加载完毕
        }
        onData(data, refresh);
    }

    @Override
    public void onComplete(String path) {
        mLoading = false;
        hideLoading();
    }

    protected abstract BasePagePresenter getBasePagePresenter();
    protected abstract TypeToken getTypeToken();

    protected void onData(List data, boolean refresh) {

    }
}
