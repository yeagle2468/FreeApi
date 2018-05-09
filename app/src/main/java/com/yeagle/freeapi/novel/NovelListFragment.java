package com.yeagle.freeapi.novel;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.ApiRecyclerFragment;
import com.yeagle.freeapi.base.BasePagePresenter;
import com.yeagle.freeapi.network.api.Api;
import com.yeagle.freeapi.novel.adapter.NovelAdapter;
import com.yeagle.freeapi.novel.model.NovelInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yeagle on 2018/5/7.
 */
public class NovelListFragment extends ApiRecyclerFragment {
    private static final int DEFAULT_PAGE_NUM = 4;

    @Inject
    BasePagePresenter mPagePresenter;
    private NovelAdapter mAdapter;

    @BindView(R.id.title_name_tv)
    TextView mTitleNameView;

    @BindView(R.id.title_back_rl)
    View mBackView;

    @Inject
    public NovelListFragment(){
        Bundle bundle = new Bundle();
        bundle.putString(PATH, Api.NOVEL_PATH);
        setArguments(bundle);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mBackView.setVisibility(View.GONE);
        mTitleNameView.setText(R.string.novel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_novel_list;
    }

    protected int getPageNum() { //
        return DEFAULT_PAGE_NUM;
    }

    @Override
    protected BasePagePresenter getBasePagePresenter() {
        return mPagePresenter;
    }

    @Override
    protected TypeToken getTypeToken() {
        return new TypeToken<List<NovelInfo>>(){};
    }

    @Override
    protected void onData(List data, boolean refresh) {
        super.onData(data, refresh);

        if (mAdapter == null) {
            mAdapter = new NovelAdapter(getContext(), (List<NovelInfo>) data);
            mRcView.setAdapter(mAdapter);
        } else if (refresh) {
            mAdapter.setData((List<NovelInfo>) data);
        } else {
            mAdapter.addData((List<NovelInfo>) data);
//            LogUtils.e(TAG, "adapter data size:" + mAdapter.getItemCount() + this);
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new NovelAdapter(getContext(), new ArrayList<>());
        return mAdapter;
    }
}
