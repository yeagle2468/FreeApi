package com.yeagle.freeapi.base;

import android.os.Bundle;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by yeagle on 2018/5/2.
 */
public class ApiRecyclerFragment extends BaseRecyclerFragment {
    protected static final String PATH = "path";
    protected static final String TYPE = "type";

    @Inject
    BasePagePresenter mPagePresenter;

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
        mPagePresenter.loadData(bundle.getString(PATH), refresh, bundle.getInt(TYPE), new ErrorHandleSubscriber(mPagePresenter.getRxErrorHandler()) {
            @Override
            public void onNext(Object o) {
                onData(o, refresh);
            }

            @Override
            public void onComplete() {
                mLoading = false;
            }
        });
    }

    protected void onData(Object object, boolean refresh) {

    }
}
