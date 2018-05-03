package com.yeagle.freeapi.base;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.yeagle.common.mvp.IPresenter;
import cn.yeagle.common.mvp.IView;

/**
 * Created by yeagle on 2018/5/2.
 */
public interface PageContract {
    interface View extends IView {
        public void onData(List data, boolean refresh, String path);
        public void onComplete(String path);
    }

    public interface Presenter<View> extends IPresenter<PageContract.View>{
        void loadData(String path, boolean refresh, TypeToken token);
        void loadData(String path, boolean refresh, Object extraValue, TypeToken token);
    }
}
