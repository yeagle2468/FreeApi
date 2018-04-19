package com.yeagle.freeapi.util;

import okhttp3.OkHttpClient;

/**
 * Created by yeagle on 2018/1/31.
 */
public class OkHttpUtils {

    /**
     *
     * @param client
     * @param host
     * @return
     */
    public static OkHttpClient addInterceptor(OkHttpClient client, String host) {
        OkHttpClient.Builder builder = client.newBuilder();
//        builder.addInterceptor(new OkHttpInterceptor(host));
        return builder.build();
    }
}
