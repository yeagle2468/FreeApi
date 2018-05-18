package cn.yeagle.common.mvp;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

import cn.yeagle.common.http.IRepositoryManager;
import io.reactivex.Observable;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by yeagle on 2018/4/26.
 */
public abstract class BasePagePresenter extends BasePresenter<PageContract.View> implements PageContract.Presenter<PageContract.View>{
//    private int page;
    protected RxErrorHandler mErrorHandler;
    protected IRepositoryManager mRepositoryManager;

    private HashMap<String, Integer> mPages = new HashMap<>();

    public BasePagePresenter(RxErrorHandler handler, IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
        this.mErrorHandler = handler;
    }

    @Override
    public void loadData(String path, boolean refresh, TypeToken token) {
        loadData(path, refresh, null, token);
    }

    @Override
    public void loadData(final String path, final boolean refresh, Object extraValue, TypeToken token) {
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
        doRequest(observable, new ErrorHandleSubscriber(mErrorHandler) {
            @Override
            public void onNext(Object o) {
                if (mView != null)
                    mView.onData((List)o, refresh, path);
            }

            @Override
            public void onComplete() {
                if (mView != null)
                    mView.onComplete(path);
            }
        }, token);
    }

    public RxErrorHandler getRxErrorHandler() {
        return mErrorHandler;
    }

    @Override
    protected String convertToString(Object object) {
        String str = object.toString();//((BaseBean<JsonElement>)object).getData().toString();
//        LogUtils.e(TAG, "content;" + str);

        return str;
    }

    public int getPage(String path) {
        if (mPages.containsKey(path))
            mPages.get(path);

        return 0;
    }

    protected abstract Observable getObservable(String path, int page, Object extraValue);
}
