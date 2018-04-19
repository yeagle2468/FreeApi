package cn.yeagle.common.mvp;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import cn.yeagle.common.http.RequestRetryHandler;
import dagger.internal.Preconditions;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by yeagle on 2018/1/23.
 */
public abstract class BasePresenter<V extends cn.yeagle.common.mvp.IView> implements IPresenter<V> { //<V extends IView>
    protected final String TAG = this.getClass().getSimpleName();
    protected V mView;

    private static final RequestRetryHandler mRetryDefaultHander = new RequestRetryHandler(3, 200);

//    public BasePresenter(RxErrorHandler errorHandler) {
//
//    }

    public void takeView(V view) {
        Preconditions.checkNotNull(view, "%s can't be null", IView.class.getName());
        mView = view;
    }

    public void dropView() {
        mView = null;
    }

    public BasePresenter() {
    }

    public boolean useEventBus() {
        return false;
    }

    @NonNull
    @CheckResult
    public final Observable lifecycle() {
        return convertViewToLifeCycle().lifecycle();
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return convertViewToLifeCycle().bindUntilEvent(event);
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return convertViewToLifeCycle().bindUntilEvent(event);
    }

    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return convertViewToLifeCycle().bindToLifecycle();
    }

    /**
     * 本来一直想用{@link #bindToLifecycle()}，但是这个会存在问题，
     * 一个栗子，在启动一个activity的后，发起请求，请求居然直接取消了，这说明在Stop或者在pause的时候就取消了，so必要的时候要自己设置until Destroy
     * @return
     */
    protected LifecycleTransformer getLifecycleTransformer() {
        return bindToLifecycle();
    }

    private LifecycleProvider convertViewToLifeCycle() {
        if (mView == null || !(mView instanceof LifecycleProvider)) {
            if (mView == null)
                throw new NullPointerException("View is null NullPointerException");
            else
                throw new IllegalArgumentException("View is not implements LifecycleProvider");
        }

        return (LifecycleProvider) mView;
    }

    /**
     * 请求不需要重新请求
     * @param observable
     * @param onFinally
     * @param subscriber
     */
    protected void doRequestNoRetery(Observable observable, Action onFinally, ErrorHandleSubscriber subscriber) {
        doRequest(observable, null, onFinally, subscriber);
    }

    protected void doRequest(Observable observable, Action onFinally, ErrorHandleSubscriber subscriber) {
        doRequest(observable, mRetryDefaultHander, onFinally, subscriber);
    }

    /**
     * 抽出公共部分
     * 这个函数后面也要重写，把json转成Object的地方放在map里面转 很方便
     * @param observable 要操作的部分
     * @param handler 做重试处理
     * @param onFinally 最好要做的操作
     * @param subscriber 出结果及异常处理
     */
    protected void doRequest(Observable observable, RequestRetryHandler handler, Action onFinally, ErrorHandleSubscriber subscriber) {
        observable.subscribeOn(Schedulers.io())
                .retryWhen(handler)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(onFinally)
                .compose(getLifecycleTransformer())//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁 //<List<UserInfo>>
                .subscribe(subscriber);
    }

    @Override
    public void onStart() {
        if (useEventBus())
            EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        if (useEventBus())
            EventBus.getDefault().unregister(this);

        dropView();
    }
}
