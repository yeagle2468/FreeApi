package com.yeagle.freeapi.di;

import android.content.Context;

import com.google.gson.Gson;
import com.yeagle.freeapi.api.Api;
import com.yeagle.freeapi.base.ApiPagePresenter;
import com.yeagle.freeapi.base.BasePagePresenter;
import com.yeagle.freeapi.network.ResponseError;
import com.yeagle.freeapi.network.converter.MyGsonConverterFactory;
import com.yeagle.freeapi.util.OkHttpUtils;

import java.io.File;

import javax.inject.Singleton;

import com.yeagle.freeapi.network.CustomOkHttpClient;

import cn.yeagle.common.di.scope.ActivityScoped;
import cn.yeagle.common.http.IRepositoryManager;
import cn.yeagle.common.http.RepositoryManager;
import cn.yeagle.common.utils.CacheDirUtils;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 全局提供的变量
 * dagger提供的单例模式
 */
@Module
public abstract class GlobalConfigModule {
    @Singleton
    @Binds // binds use simple
    abstract IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager);

    @Singleton
    @Binds
    abstract ResponseErrorListener provideResponseErrorListener(ResponseError error);

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(Context application) {
        return CustomOkHttpClient.getOkHttpClient(application);
    }

    @Singleton
    @Provides
    static RxErrorHandler provideRxErrorHandler (Context application, ResponseErrorListener listener) {
        return RxErrorHandler
                .builder()
                .with(application)
                .responseErrorListener(listener)
                .build();
    }

    @Singleton
    @Provides
    static File provideCacheDir(Context application) {
        return CacheDirUtils.getCacheDir(application);
    }

    @Singleton
    @Provides
    static String provideBaseUrl() {
        return Api.APP_DOMAIN;
    }

    @Singleton
    @Provides
    static Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    static RxCache provideRxCache(File cacheDir) {
        RxCache.Builder builder = new RxCache.Builder();
        return builder.persistence(cacheDir, new GsonSpeaker());
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(String url, OkHttpClient client, Gson gson) {
        Retrofit.Builder builder = new Retrofit.Builder();

        builder.baseUrl(url)//域名
                .client(OkHttpUtils.addInterceptor(client, url));

        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 Rxjava
                .addConverterFactory(MyGsonConverterFactory.create(gson));//使用 Gson GsonConverterFactory.create(gson)
        return builder.build();
    }


}
