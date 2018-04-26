package com.yeagle.freeapi.widget.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paginate.recycler.LoadingListItemCreator;
import com.yeagle.freeapi.R;

/**
 * Created by yeagle on 2018/4/25.
 */
public class CustomLoadingListItemCreator implements LoadingListItemCreator {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_loading_layout, parent, false);
        return new RecyclerView.ViewHolder(view){};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        VH vh = (VH) holder;
//        vh.tvLoading.setText(String.format("Total items loaded: %d.\nLoading more...", adapter.getItemCount()));
//
//        // This is how you can make full span if you are using StaggeredGridLayoutManager
//        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
//            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) vh.itemView.getLayoutParams();
//            params.setFullSpan(true);
//        }
    }

//    static class VH extends RecyclerView.ViewHolder {
//        TextView tvLoading;
//
//        public VH(View itemView) {
//            super(itemView);
//            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading_text);
//        }
//    }
}
