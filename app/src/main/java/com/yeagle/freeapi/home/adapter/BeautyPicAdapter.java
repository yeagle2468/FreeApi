package com.yeagle.freeapi.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeagle.freeapi.R;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import com.yeagle.freeapi.home.model.BeautyInfo;

import java.util.List;

/**
 * Created by yeagle on 2018/5/2.
 */
public class BeautyPicAdapter extends CommonAdapter<BeautyInfo> {
    public BeautyPicAdapter(Context context,  List<BeautyInfo> datas) { //int layoutId,
        super(context, R.layout.rc_item_beauty, datas);
    }

    @Override
    protected void convert(ViewHolder holder, BeautyInfo info, int position) {
        ImageView imageView = holder.getView(R.id.iv_img);

        Glide.with(imageView)
                .load(info.url)
//                .error(R.drawable.default_pic_2)
                .into(imageView);
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
