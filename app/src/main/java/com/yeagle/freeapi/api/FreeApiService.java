package com.yeagle.freeapi.api;

import com.yeagle.freeapi.home.model.SatinGodInfo;
import com.yeagle.freeapi.home.model.SatinInfo;
import com.yeagle.freeapi.network.base.BaseBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yeagle on 2018/4/17.
 */
public interface FreeApiService { //satinGodApi
    //https://www.apiopen.top/satinApi?type=1&page=1
    @GET("satinApi")
    BaseBean<List<SatinInfo>> satinApi(@Query("type") int type, @Query("page") int page);
    @GET("satinGodApi")
    BaseBean<List<SatinGodInfo>> satinGodApi(@Query("type") int type, @Query("page") int page);

    @GET("{path}")
    BaseBean<List<SatinGodInfo>> request(@Path(value = "path", encoded = true)String path, @Query("type") int type, @Query("page") int page);
}
