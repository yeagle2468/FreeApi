package com.yeagle.freeapi.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.photo.PictureActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import com.yeagle.freeapi.home.model.BeautyInfo;

import java.util.ArrayList;
import java.util.List;

import cn.yeagle.common.utils.ActivityUtils;

/**
 * Created by yeagle on 2018/5/2.
 */
public class BeautyPicAdapter extends CommonAdapter<BeautyInfo> {
    RequestOptions options = new RequestOptions().dontAnimate().placeholder(R.drawable.default_picture).priority(Priority.HIGH);

    public BeautyPicAdapter(Context context,  List<BeautyInfo> datas) { //int layoutId,
        super(context, R.layout.rc_item_beauty, datas);
    }

    @Override
    protected void convert(ViewHolder holder, BeautyInfo info, final int position) {
        ImageView imageView = holder.getView(R.id.iv_img);

//        LogUtils.e("BeautyPicAdapter", "convert:" + info.url + "::" + position);
        Glide.with(mContext)
                .load(info.url)
//                .error(R.drawable.default_pic_2)
                .apply(options)
                .into(imageView);

        holder.itemView.setOnClickListener((view -> { PictureActivity.launch(ActivityUtils.getActivityFromView(view), (ArrayList)mDatas, position, info.type);
//        LogUtils.e("BPA", "pos:" + position + ",url:" + info.url);
        }
           ));
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
//        mDatas = data; // 父类跟父父类都定义了一个成员变量mDatas，其实父类那个成员变量没点用，导致这样直接赋值不能刷新只能这样操作才可以
        mDatas.clear();
        addData(data);
        notifyDataSetChanged();
    }
}
