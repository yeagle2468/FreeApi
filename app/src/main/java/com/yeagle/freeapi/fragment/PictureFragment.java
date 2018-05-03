package com.yeagle.freeapi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.rcsing.R;

/**
 */
public class PictureFragment extends BaseFragment {
    private View mRootView;
    private static DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.default_image)    // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.default_image)        // 设置图片加载或解码过程中发生错误显示的图片
            .showImageOnLoading(R.drawable.default_image)        //
            .cacheInMemory(false)                        // 设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                            // 设置下载的图片是否缓存在SD卡中
            .considerExifParams(true)
            .imageScaleType(ImageScaleType.EXACTLY)
            .build();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            final View view = inflater.inflate(R.layout.simple_picture_layout, container, false);
            mRootView = view;
        }

        if (mRootView.getParent() != null) {
            ((ViewGroup)mRootView.getParent()).removeView(mRootView);
        }
        final ImageView iv = (ImageView)mRootView.findViewById(R.id.photo);
        iv.post(new Runnable(){
            @Override
            public void run(){
                Bundle bundle = getArguments();
                ImageLoader.getInstance().displayImage(bundle.getString("url"), iv, mOptions);
            }
        });
//        Bundle bundle = getArguments();
//        LogUtil.d("PictureFragment", "URL:" + bundle.getString("url") + ",pos:" + bundle.getInt("pos"));

        return mRootView;
    }

}
