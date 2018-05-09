package com.yeagle.freeapi.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.home.model.SatinInfo;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import cn.yeagle.common.utils.LogUtils;

/**
 * Created by yeagle on 2018/5/9.
 */
public class PictureItemDelegate extends BaseSatinItemDelegate {
    public PictureItemDelegate() {
        super(SatinInfo.TYPE_IMAGE);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_satin_picture;
    }

    @Override
    public void convert(ViewHolder holder, SatinInfo o, int position) {
        super.convert(holder, o, position);

        ImageView imgView = holder.getView(R.id.iv_img);
        if (o.is_gif) {
            Glide.with(imgView.getContext()).asGif().load(o.cdn_img).apply(options).into(imgView);
            LogUtils.e("PID", "is gif:" + o.name + "::P:" + position );
        } else
            Glide.with(imgView.getContext()).load(o.cdn_img).apply(options).into(imgView);
    }
}
