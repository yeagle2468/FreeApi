package cn.yeagle.common.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import cn.yeagle.common.http.CustomOkHttpClient;
import okhttp3.OkHttpClient;

/**
 * Created by yeagle on 2018/5/3.
 */
@GlideModule
public class OkHttpAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, @NonNull Glide glide, Registry registry) {
        OkHttpClient client = CustomOkHttpClient.getUnsafeOkHttpClient();//UnsafeOkHttpClient.getUnsafeOkHttpClient();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
    }
}
