package com.yeagle.freeapi.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.home.model.SatinInfo;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by yeagle on 2018/5/9.
 */
public class VideoItemDelegate extends BaseSatinItemDelegate {
    public VideoItemDelegate() {
        super(SatinInfo.TYPE_VIDEO);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_satin_video;
    }

    @Override
    public void convert(ViewHolder holder, SatinInfo o, int position) {
        super.convert(holder, o, position);
        ImageView imgView = holder.getView(R.id.iv_img);

        Glide.with(imgView.getContext()).load(o.bimageuri).apply(options).into(imgView);
    }
}
