package com.yeagle.freeapi.base;

import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.yeagle.freeapi.home.model.BeautyInfo;

import java.util.List;

import javax.inject.Inject;

import cn.yeagle.common.utils.LogUtils;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

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

        pagePresenter.loadData(bundle.getString(PATH), refresh, bundle.getInt(TYPE), new TypeToken<List<BeautyInfo>>(){}); // getTypeToken()
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
    public void onData(Object object, boolean refresh, String path) {
        onData(object, refresh);
    }

    @Override
    public void onComplete(String path) {
        mLoading = false;
        hideLoading();
    }

    protected abstract BasePagePresenter getBasePagePresenter();
    protected abstract TypeToken getTypeToken();

    protected void onData(Object object, boolean refresh) {

    }
}
