package com.yeagle.freeapi.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.yeagle.freeapi.api.Api;
import com.yeagle.freeapi.base.ApiRecyclerFragment;
import com.yeagle.freeapi.home.adapter.BeautyPicAdapter;
import com.yeagle.freeapi.home.model.BeautyInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.yeagle.common.di.scope.ActivityScoped;

/**
 * Created by yeagle on 2018/5/2.
 * 美图
 */
@ActivityScoped
public class BeautyPicFragment extends ApiRecyclerFragment {
    private BeautyPicAdapter mAdapter;

    @Inject
    public BeautyPicFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(PATH, Api.BEAUTY_PATH);
        setArguments(bundle);
    }

    @Override
    protected void initAdapter() {
        mRcView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRcView.setAdapter(new BeautyPicAdapter(getContext(), new ArrayList<>()));
    }

    @Override
    protected void onData(Object object, boolean refresh) {
        super.onData(object, refresh);

        if (mAdapter == null) {
            mAdapter = new BeautyPicAdapter(getContext(), (List<BeautyInfo>) object);
        } else if (refresh) {
            mAdapter.setData((List<BeautyInfo>) object);
        } else {
            mAdapter.addData((List<BeautyInfo>) object);
        }
    }

    @Override
    public boolean useInject() {
        return false;
    }
}
