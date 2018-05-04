package com.yeagle.freeapi.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.LazyFragment;

import butterknife.BindView;
import cn.yeagle.common.utils.LogUtils;

/**
 */
public class PictureFragment extends LazyFragment {
    private static final String URL = "url";

    @BindView(R.id.photo_view)
    PhotoView mPhotoView;

    @Override
    public int getLayoutId() {
        return R.layout.simple_picture_layout;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        LogUtils.e(TAG, "container:" + container.getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mPhotoView.setImageResource(R.drawable.default_picture);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();

        if (mPhotoView == null) {
            LogUtils.e(TAG, "lazyLoad: mPhotoView is null");
            return;
        }
        LogUtils.e(TAG, getArguments().getString(URL));

        RequestOptions options = new RequestOptions().dontAnimate().placeholder(R.drawable.default_picture).priority(Priority.HIGH);
        Glide.with(getContext()).load(getArguments().getString(URL)).apply(options).into(mPhotoView);
    }

    @Override
    public boolean useInject() {
        return false;
    }

    public static PictureFragment newInstanace(String url) {
        PictureFragment fragment = new PictureFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);

        fragment.setArguments(bundle);

        return fragment;
    }
}
