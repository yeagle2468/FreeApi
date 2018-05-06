package com.yeagle.freeapi.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.paginate.Paginate;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.home.adapter.BeautyPicAdapter;
import com.yeagle.freeapi.widget.recycleview.CustomLoadingListItemCreator;

import java.util.ArrayList;

import cn.yeagle.common.utils.LogUtils;

/**
 * Created by yeagle on 2018/4/18.
 */
public abstract class BaseRecyclerFragment extends LazyFragment implements Paginate.Callbacks {
    private static final int GRID_SPAN = 3;
    private static final int DEFAULT_PAGE_NUM = 20;

    protected RecyclerView mRcView;
    protected SwipeRefreshLayout mSwipeLayout;
    protected Paginate mPaginate;
    protected boolean mLoading;

    protected View mLoadingView;
    protected boolean mLoadedFirstData;
    protected boolean hasLoadedAllData;

    private RecyclerView.Adapter mAdapter;
//    private boolean

    @Override
    public int getLayoutId() {
        return R.layout.layout_recycler_refresh;
    }

    @Override
    protected void initViews() {
        mRcView = mRootView.findViewById(R.id.recyclerView);
        mSwipeLayout = mRootView.findViewById(R.id.swipeLayout);
        mLoadingView = mRootView.findViewById(R.id.loadingView);

        if (mSwipeLayout != null) {
            mSwipeLayout.setOnRefreshListener(() -> onRefresh());
        }

        mRcView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 滑动的时候暂停加载图片
                    Glide.with(getContext()).resumeRequests(); // 现在不是针对所有的有效了，只对这个context有效
//                    LogUtils.e(TAG, "resumeRequests");
                } else {  // 滑动的时候暂停加载图片
                    Glide.with(getContext()).pauseAllRequests(); // 现在不是针对所有的有效了，只对这个context有效
//                    LogUtils.e(TAG, "pauseAllRequests");
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        initAdapter();
        mPaginate = Paginate.with(mRcView, this)
                .setLoadingTriggerThreshold(0)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(new CustomLoadingListItemCreator())
                .setLoadingListItemSpanSizeLookup(() -> GRID_SPAN)
                .build();
//        mPaginate.setHasMoreDataToLoad(false);
    }

    protected void initAdapter() {
        mRcView.setLayoutManager(getLayoutManager());
        RecyclerView.Adapter adapter ;
        if (mAdapter == null)
            adapter = mAdapter;
        else {
            adapter = getAdapter();
            mAdapter = adapter;
        }
        mRcView.setAdapter(adapter);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    protected abstract RecyclerView.Adapter getAdapter();

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        onRefresh();
    }

    protected void onRefresh() {
        mLoading = true;
        LogUtils.e(TAG, "onRefresh");
    }

    protected int getPageNum() {
        return DEFAULT_PAGE_NUM;
    }

    @Override
    public void onLoadMore() {
        LogUtils.e(TAG, "onLoadMore");
        mLoading = true;
    }

    @Override
    public synchronized boolean isLoading() {
        LogUtils.e(TAG, "isLoading:" + mLoading);
        return mLoading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        LogUtils.e(TAG, "hasLoadedAllItems:" + (!mLoadedFirstData || hasLoadedAllData));
        return ((!mLoadedFirstData) || hasLoadedAllData);
    }
}
