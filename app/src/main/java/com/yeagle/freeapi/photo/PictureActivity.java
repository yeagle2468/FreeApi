package com.yeagle.freeapi.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.FreeApiActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.yeagle.common.adapter.CacheFragmentAdapter;

/**
 * Created by yeagle on 2018/5/3.
 */
public class PictureActivity extends FreeApiActivity {
    public static final String INFO = "info";
    private static final String POS = "pos";
    private static final String TITLE = "title";

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.title_name_tv)
    TextView mTitleTv;

    @BindView(R.id.title_more_view)
    View mMoreView;

    private String mPicName;

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

        final CacheFragmentAdapter<PictureFragment> adapter = new CacheFragmentAdapter<>(getSupportFragmentManager(), list, PictureFragment.class);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTitleTv.setText(getString(R.string.picture_num, mPicName, position+1, adapter.getCount()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        Intent intent = getIntent();
        final int pos = intent.getIntExtra(POS, 0);
        final int count = adapter.getCount();
        if (pos >= 0 && pos < count) {
            mViewPager.setCurrentItem(pos);
        }
        String title = intent.getStringExtra(TITLE);
        mPicName = title == null ?  getString(R.string.picture) : title;

        mTitleTv.setText(getString(R.string.picture_num, mPicName, pos+1, count));
    }

    @OnClick(R.id.title_more_view)
    void onClickMoreView() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POS, mViewPager.getCurrentItem());
    }

    public static void launch(Context context, ArrayList<IPhotoInfo> list) {
        launch(context, list, 0);
    }

    public static void launch(Context context, ArrayList<IPhotoInfo> list, int pos, String title) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putParcelableArrayListExtra(INFO, list);
        intent.putExtra(POS, pos);
        if (title != null)
            intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    public static void launch(Context context, ArrayList<IPhotoInfo> list, int pos) {
        launch(context, list, pos, null);
    }

    @Override
    public boolean useInject() {
        return false;
    }
}
