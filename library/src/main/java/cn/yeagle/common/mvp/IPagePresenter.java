package cn.yeagle.common.mvp;

import cn.yeagle.common.mvp.IPresenter;
import cn.yeagle.common.mvp.IView;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by yeagle on 2018/4/26.
 */
public interface IPagePresenter<V extends IView> extends IPresenter<V> {
    void loadData(String path, boolean refresh, ErrorHandleSubscriber subscriber);
    void loadData(String path, boolean refresh, Object extraValue, ErrorHandleSubscriber subscriber);
}
