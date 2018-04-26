package com.yeagle.freeapi.network;


import android.annotation.SuppressLint;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import cn.yeagle.common.BuildConfig;
import okhttp3.OkHttpClient;

/**
 * Created by xc on 2017/6/30.
 * 定制的okclient，包含ssl证书校验,包含请求拦截
 */
public class CustomOkHttpClient {

    public static OkHttpClient getOkHttpClient(Context context) {
        try {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //线上环境校验应用证书
            if (!BuildConfig.DEBUG) {         //测试环境，不验证ssl方便测试抓包
                builder.sslSocketFactory(getSSLContext(context).getSocketFactory());
            } else {
//            忽略证书
                SSLSocketFactory sslSocketFactory = createSSLSocketFactory();
                if (sslSocketFactory != null) {
                    builder.sslSocketFactory(sslSocketFactory);
                }
            }
            //host验证,这里host不验证
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            OkHttpClient build = builder.build();
            return build;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取不安全的client
     *
     * @return
     */
    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
//            忽略证书
            SSLSocketFactory sslSocketFactory = createSSLSocketFactory();
            if (sslSocketFactory != null) {
                mBuilder.sslSocketFactory(sslSocketFactory);
            }
            //host验证,这里host不验证
            mBuilder.hostnameVerifier((hostname, session) -> {return true;});
            OkHttpClient build = mBuilder.build();
            return build;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Https 证书验证对象
     */
    private static SSLContext s_sSLContext = null;

    /**
     * 获取Https的证书
     *
     * @param context Activity（fragment）的上下文
     * @return SSL的上下文对象
     */
    private static SSLContext getSSLContext(Context context) {
        if (null != s_sSLContext) {
            return s_sSLContext;
        }

        CertificateFactory certificateFactory = null;

        InputStream inputStream = null;
        KeyStore keystore = null;
        String tmfAlgorithm = null;
        TrustManagerFactory trustManagerFactory = null;
        try {
            certificateFactory = CertificateFactory.getInstance("X.509", "BC");

            inputStream = context.getAssets().open("cert.cer");//这里导入SSL证书文件

            Certificate ca = certificateFactory.generateCertificate(inputStream);

            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(null, null);
            keystore.setCertificateEntry("ca", ca);

            tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keystore);

            s_sSLContext = SSLContext.getInstance("TLS");
            s_sSLContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return s_sSLContext;
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 默认信任所有的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sSLSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}