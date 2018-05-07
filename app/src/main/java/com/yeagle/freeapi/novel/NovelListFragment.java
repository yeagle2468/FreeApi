package com.yeagle.freeapi.novel;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.yeagle.freeapi.base.ApiRecyclerFragment;
import com.yeagle.freeapi.base.BasePagePresenter;
import com.yeagle.freeapi.network.api.Api;
import com.yeagle.freeapi.novel.adapter.NovelAdapter;
import com.yeagle.freeapi.novel.model.NovelInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yeagle on 2018/5/7.
 */
public class NovelListFragment extends ApiRecyclerFragment {
    @Inject
    BasePagePresenter mPagePresenter;
    private NovelAdapter mAdapter;

    @Inject
    public NovelListFragment(){
        Bundle bundle = new Bundle();
        bundle.putString(PATH, Api.NOVEL_PATH);
        setArguments(bundle);
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
//        mRcView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
        return new NovelAdapter(getContext(), new ArrayList<>());
    }
}
