package com.yeagle.freeapi.base;

import com.yeagle.freeapi.network.api.FreeApiService;

import javax.inject.Inject;

import cn.yeagle.common.http.IRepositoryManager;
import cn.yeagle.common.mvp.BasePagePresenter;
import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 *
 */
public class ApiPagePresenter extends BasePagePresenter {
    @Inject
    public ApiPagePresenter(RxErrorHandler handler, IRepositoryManager repositoryManager) {
        super(handler, repositoryManager);
    }

    @Override
    protected Observable getObservable(String path, int page, Object extraValue) {
        return mRepositoryManager.obtainRetrofitService(FreeApiService.class).request(path, (int)extraValue, page);
    }
}
