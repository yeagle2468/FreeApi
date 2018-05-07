package com.yeagle.freeapi.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.BaseRcAdapter;
import com.yeagle.freeapi.photo.PictureActivity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import com.yeagle.freeapi.home.model.BeautyInfo;

import java.util.ArrayList;
import java.util.List;

import cn.yeagle.common.utils.ActivityUtils;

/**
 * Created by yeagle on 2018/5/2.
 */
public class BeautyPicAdapter extends BaseRcAdapter<BeautyInfo> {
    public BeautyPicAdapter(Context context,  List<BeautyInfo> datas) { //int layoutId,
        super(context, R.layout.rc_item_beauty, datas);
    }

    @Override
    protected void convert(ViewHolder holder, BeautyInfo info, final int position) {
        ImageView imageView = holder.getView(R.id.iv_img);

        Glide.with(mContext)
                .load(info.url)
                .apply(options)
                .into(imageView);

        holder.itemView.setOnClickListener((view -> { PictureActivity.launch(ActivityUtils.getActivityFromView(view), (ArrayList)mDatas, position, info.type);
//        LogUtils.e("BPA", "pos:" + position + ",url:" + info.url);
        }
           ));
    }
}
