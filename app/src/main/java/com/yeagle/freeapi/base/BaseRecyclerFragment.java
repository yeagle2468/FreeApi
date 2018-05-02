package com.yeagle.freeapi.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.paginate.Paginate;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.widget.recycleview.CustomLoadingListItemCreator;

import javax.inject.Inject;

import cn.yeagle.common.base.BaseFragment;

/**
 * Created by yeagle on 2018/4/18.
 */
public class BaseRecyclerFragment extends LazyFragment implements Paginate.Callbacks {
    private static final int GRID_SPAN = 3;
    protected RecyclerView mRcView;
    protected SwipeRefreshLayout mSwipeLayout;
    protected Paginate mPaginate;
    protected boolean mLoading;

    @Override
    public int getLayoutId() {
        return R.layout.layout_recycler_refresh;
    }

    @Override
    protected void initViews() {
        mRcView = mRootView.findViewById(R.id.recyclerView);
        mSwipeLayout = mRootView.findViewById(R.id.swipeLayout);

        if (mSwipeLayout != null) {
            mSwipeLayout.setOnRefreshListener(() -> onRefresh());
        }

        initAdapter();
        mPaginate = Paginate.with(mRcView, this)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(new CustomLoadingListItemCreator())
                .setLoadingListItemSpanSizeLookup(() -> GRID_SPAN)
                .build();
    }

    protected void initAdapter() {

    }

    @Override
    protected void lazyLoad() {
        onRefresh();
    }

    protected void onRefresh() {
        mLoading = true;
    }

    @Override
    public void onLoadMore() {
        mLoading = true;
    }

    @Override
    public synchronized boolean isLoading() {
        return mLoading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return false;
    }
}
