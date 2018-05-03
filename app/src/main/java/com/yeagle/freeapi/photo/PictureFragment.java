package com.yeagle.freeapi.photo;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.LazyFragment;

import butterknife.BindView;

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

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        Glide.with(getContext()).load(getArguments().getString(URL)).into(mPhotoView);
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
