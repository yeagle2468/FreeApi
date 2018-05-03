package com.yeagle.freeapi.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.photo.PictureActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import com.yeagle.freeapi.home.model.BeautyInfo;

import java.util.ArrayList;
import java.util.List;

import cn.yeagle.common.utils.ActivityUtils;
import cn.yeagle.common.utils.LogUtils;

/**
 * Created by yeagle on 2018/5/2.
 */
public class BeautyPicAdapter extends CommonAdapter<BeautyInfo> {
    public BeautyPicAdapter(Context context,  List<BeautyInfo> datas) { //int layoutId,
        super(context, R.layout.rc_item_beauty, datas);
    }

    @Override
    protected void convert(ViewHolder holder, BeautyInfo info, final int position) {
        ImageView imageView = holder.getView(R.id.iv_img);

//        LogUtils.e("BeautyPicAdapter", "convert:" + info.url);
        Glide.with(mContext)
                .load(info.url)
//                .error(R.drawable.default_pic_2)
                .into(imageView);

        holder.itemView.setOnClickListener((view ->
            PictureActivity.launch(ActivityUtils.getActivityFromView(view), (ArrayList)mDatas, position)));
    }

    public void addData(List<BeautyInfo> data) {
        if (mDatas != null)
            mDatas.addAll(data);
        else {
            mDatas = data;
        }

        notifyDataSetChanged();
    }

    public void setData(List<BeautyInfo> data) {
        mDatas = data;
        notifyDataSetChanged();
    }
}
