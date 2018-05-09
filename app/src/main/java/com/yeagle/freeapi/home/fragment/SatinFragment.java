package com.yeagle.freeapi.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.yeagle.freeapi.base.ApiRecyclerFragment;
import com.yeagle.freeapi.base.BasePagePresenter;
import com.yeagle.freeapi.home.adapter.PictureItemDelegate;
import com.yeagle.freeapi.home.adapter.TextItemDelegate;
import com.yeagle.freeapi.home.adapter.VideoItemDelegate;
import com.yeagle.freeapi.home.adapter.VoiceItemDelegate;
import com.yeagle.freeapi.home.model.SatinInfo;
import com.yeagle.freeapi.network.api.Api;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yeagle on 2018/4/18.
 */
public class SatinFragment extends ApiRecyclerFragment {
    private List<SatinInfo> mDatas;

    @Inject
    BasePagePresenter mPagePresenter;

    private MultiItemTypeAdapter mAdapter;

    @Inject
    public SatinFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(PATH, Api.SATIN_PATH);
        setArguments(bundle);
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        List<SatinInfo> data = new ArrayList<>();
        mDatas = data;
        MultiItemTypeAdapter<SatinInfo> adapter = new MultiItemTypeAdapter<>(getContext(), data);
        mAdapter = adapter;

        adapter.addItemViewDelegate(new TextItemDelegate());
        adapter.addItemViewDelegate(new PictureItemDelegate());
        adapter.addItemViewDelegate(new VoiceItemDelegate());
        adapter.addItemViewDelegate(new VideoItemDelegate());

        return adapter;
    }


    @Override
    public void onData(List data, boolean refresh, String path) {
        super.onData(data, refresh, path);

        if (refresh) {
            mDatas.clear();
            mDatas.addAll(data);
        } else {
            mDatas.addAll(data);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected boolean scrollShowPicture() {
        return true;
    }

    @Override
    protected BasePagePresenter getBasePagePresenter() {
        return mPagePresenter;
    }

    @Override
    protected TypeToken getTypeToken() {
        return new TypeToken<List<SatinInfo>>(){};
    }
}
