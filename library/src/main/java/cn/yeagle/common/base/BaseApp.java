package cn.yeagle.common.base;

import android.content.Context;
import android.util.Log;

import cn.yeagle.common.task.TaskExecutor;
import dagger.android.support.DaggerApplication;

/**
 * 这个就必须支持inject，否则就不能使用了
 * 说明：
 *      根据阿里巴巴的开发手册，不要在application中缓存数据，开始有些疑惑，感觉全局变量这里保存还是比较方便。
 *      其实这里保存一个最大的问题就是，崩溃后数据不会save，像activity跟fragment都会保存
 *      so 像需要保存的数据，要么放在上述的里面save，要么通过保存到本地
 *
 *      如果有多个进程，那么要针对不同的进程初始化，避免不必要的初始化
 */
public abstract class BaseApp extends DaggerApplication {
    private final String TAG = this.getClass().getSimpleName();
    private static Context mContext;

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
        mContext = this;

        TaskExecutor.createInstance();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }
}
