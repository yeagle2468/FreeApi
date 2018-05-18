package com.yeagle.freeapi.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.reflect.TypeToken;
import com.yeagle.freeapi.base.ApiRecyclerFragment;

import com.yeagle.freeapi.home.adapter.PersonSignAdapter;
import com.yeagle.freeapi.home.model.Sentence;
import com.yeagle.freeapi.network.api.Api;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.yeagle.common.mvp.BasePagePresenter;

/**
 * Created by yeagle on 2018/5/7.
 */
public class PersonSignFragment extends ApiRecyclerFragment {
    @Inject
    BasePagePresenter mPagePresenter;

    @Inject
    public PersonSignFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(PATH, Api.SIGN_PATH);
        setArguments(bundle);
    }

    @Override
    protected BasePagePresenter getBasePagePresenter() {
        return mPagePresenter;
    }

    @Override
    protected TypeToken getTypeToken() {
        return new TypeToken<List<Sentence>>(){};
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new PersonSignAdapter(getContext(), new ArrayList<>());
    }
}
