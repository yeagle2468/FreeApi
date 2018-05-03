package com.yeagle.freeapi.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.FreeApiActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.yeagle.common.adapter.CacheFragmentAdapter;

/**
 * Created by yeagle on 2018/5/3.
 */
public class PictureActivity extends FreeApiActivity {
    public static final String INFO = "info";
    private static final String POS = "pos";

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    public void initViews(Bundle bundle) {
        super.initViews(bundle);

        List<IPhotoInfo> datas = getIntent().getParcelableArrayListExtra(INFO);
        initAdapter(datas);
    }

    private void initAdapter(List<IPhotoInfo> datas) {
        List<Bundle> list = new ArrayList<>();
        for (IPhotoInfo iPhotoInfo : datas) {
            Bundle bundle = new Bundle();
            bundle.putString("url", iPhotoInfo.getUrl());
            list.add(bundle);
        }

        CacheFragmentAdapter<PictureFragment> adapter = new CacheFragmentAdapter<>(getSupportFragmentManager(), list, PictureFragment.class);
        mViewPager.setAdapter(adapter);

        final int pos = getIntent().getIntExtra(POS, 0);
        if (pos >= 0 && pos < adapter.getCount()) {
            mViewPager.setCurrentItem(pos);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POS, mViewPager.getCurrentItem());
    }

    public static void launch(Context context, ArrayList<IPhotoInfo> list) {
        launch(context, list, 0);
    }

    public static void launch(Context context, ArrayList<IPhotoInfo> list, int pos) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putParcelableArrayListExtra(INFO, list);
        intent.putExtra(POS, pos);
        context.startActivity(intent);
    }

    @Override
    public boolean useInject() {
        return false;
    }
}
