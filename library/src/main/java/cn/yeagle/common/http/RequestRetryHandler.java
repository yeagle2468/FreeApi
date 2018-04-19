package cn.yeagle.common.http;

import android.util.Log;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.Function;

/**
 * 重新请求，下列几个异常将不会重新请求
 * InterruptedIOException
 * UnknownHostException
 * ConnectTimeoutException
 * SSLException
 */
public class RequestRetryHandler implements
        Function<Observable<Throwable>, ObservableSource<?>> {
    public final String TAG = this.getClass().getSimpleName();
    private final int maxRetries;
    private final int retryDelayMs;
    private int retryCount;

    public RequestRetryHandler(int maxRetries, int retryDelayMs) {
        this.maxRetries = maxRetries;
        this.retryDelayMs = retryDelayMs;
    }

    @Override
    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable.flatMap(throwable -> {
            if (throwable instanceof CompositeException) { // 这里都是通过CompositeException封装的
                List<Throwable> list = ((CompositeException) throwable).getExceptions();
                if (list != null && list.size() > 0) {
                    throwable = list.get(0);
                }
            }
            if (throwable instanceof InterruptedIOException ||
                    throwable instanceof UnknownHostException ||
                    throwable instanceof ConnectTimeoutException ||
                    throwable instanceof SSLException ||
                    ++retryCount > maxRetries) {
                Log.e(TAG, "throwable" + throwable.getClass().getSimpleName());
                return Observable.error(throwable);
            }
//                        if (++retryCount <= maxRetries) {
            // When this Observable calls onNext, the original Observable will be retried (i.e. re-subscribed).
            Log.d(TAG, "Observable get error, it will try after " + retryDelayMs
                    + " ms, retry count " + retryCount);
            return Observable.timer(retryDelayMs, TimeUnit.MILLISECONDS);
        });
    }
}
