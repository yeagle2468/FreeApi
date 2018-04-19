package cn.yeagle.common.mvp;

import android.app.Activity;

/**
 * 这里都不用泛型，BasePresenter里面使用，否则会冲突
 */
public interface IPresenter<V extends IView> {
    /**
     * 做一些初始化操作
     */
    void onStart();

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link IPresenter#onDestroy()}
     */
    void onDestroy();

    public void takeView(V view);
}
