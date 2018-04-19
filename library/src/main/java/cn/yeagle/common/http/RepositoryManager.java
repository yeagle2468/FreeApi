package cn.yeagle.common.http;

import android.support.v4.util.LruCache;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.yeagle.common.http.IRepositoryManager;
import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 *
 * */
@Singleton
public class RepositoryManager implements IRepositoryManager {
    private static final int RETROFIT_SERVICE_SIZE = 5;
    private static final int CACHE_SERVICE_SIZE = 5;

    private Lazy<Retrofit> mRetrofit;
    private Lazy<RxCache> mRxCache;
    private LruCache<String, Object> mRetrofitServiceCache = new LruCache<>(RETROFIT_SERVICE_SIZE);
    private LruCache<String, Object> mCacheServiceCache = new LruCache<>(CACHE_SERVICE_SIZE);

    @Inject
    public RepositoryManager(Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache) {
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
//        this.mCachefactory = cachefactory;
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     * @param service
     * @param <T>
     * @return
     */
    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        T retrofitService;
        synchronized (mRetrofitServiceCache) {
            retrofitService = (T) mRetrofitServiceCache.get(service.getCanonicalName());
            if (retrofitService == null) {
                retrofitService = mRetrofit.get().create(service);
                mRetrofitServiceCache.put(service.getCanonicalName(), retrofitService);
            }
        }
        return retrofitService;
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    @Override
    public <T> T obtainCacheService(Class<T> cache) {
//        if (mCacheServiceCache == null)
//            mCacheServiceCache = mCachefactory.build(CacheType.CACHE_SERVICE_CACHE);
//        Preconditions.checkNotNull(mCacheServiceCache,"Cannot return null from a Cache.Factory#build(int) method");
        T cacheService;
        synchronized (mCacheServiceCache) {
            cacheService = (T) mCacheServiceCache.get(cache.getCanonicalName());
            if (cacheService == null) {
                cacheService = mRxCache.get().using(cache);
                mCacheServiceCache.put(cache.getCanonicalName(), cacheService);
            }
        }
        return cacheService;
    }

    /**
     * 清理所有缓存
     */
    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll();
    }
}
