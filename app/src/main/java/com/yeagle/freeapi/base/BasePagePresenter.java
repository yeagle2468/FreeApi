package com.yeagle.freeapi.base;

import java.util.HashMap;

import cn.yeagle.common.http.IRepositoryManager;
import cn.yeagle.common.mvp.BasePresenter;
import cn.yeagle.common.mvp.IView;
import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by yeagle on 2018/4/26.
 */
public abstract class BasePagePresenter<V extends IView> extends BasePresenter<V> implements IPagePresenter<V> {
//    private int page;
    protected RxErrorHandler mErrorHandler;
    protected IRepositoryManager mRepositoryManager;

    private HashMap<String, Integer> mPages = new HashMap<>();

    public BasePagePresenter(RxErrorHandler handler, IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
        this.mErrorHandler = handler;
    }

    @Override
    public void loadData(String path, boolean refresh, ErrorHandleSubscriber subscriber) {
        loadData(path, refresh, null);
//        mRepositoryManager.obtainRetrofitService(FreeApiService.class).request(path, );
    }


    @Override
    public void loadData(String path, boolean refresh, Object extraValue, ErrorHandleSubscriber subscriber) {
        final int page;
        synchronized (mPages) {
            if (refresh || !mPages.containsKey(path)) {
                mPages.put(path, 0);
                page = 0;
            } else {
                page = mPages.get(path);
            }
        }

        Observable observable = getObservable(path, page, extraValue);
        doRequest(observable, subscriber);
    }

    public RxErrorHandler getRxErrorHandler() {
        return mErrorHandler;
    }

    public int getPage(String path) {
        if (mPages.containsKey(path))
            mPages.get(path);

        return 0;
    }

    protected abstract Observable getObservable(String path, int page, Object extraValue);
}
