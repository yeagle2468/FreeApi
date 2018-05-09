package com.yeagle.freeapi.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.home.model.SatinInfo;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import cn.yeagle.common.utils.LogUtils;

/**
 * Created by yeagle on 2018/5/9.
 */
public abstract class BaseSatinItemDelegate implements ItemViewDelegate<SatinInfo> {
    protected RequestOptions options = new RequestOptions().dontAnimate().placeholder(R.drawable.default_picture).priority(Priority.HIGH);
    protected int mType;

    public BaseSatinItemDelegate(int type) {
        this.mType = type;
    }

    @Override
    public boolean isForViewType(SatinInfo item, int position) {
        if (item.type == mType)
            return true;
        return false;
    }

    @Override
    public void convert(ViewHolder holder, SatinInfo satinInfo, int position) {
//        LogUtils.e("Delegate", "nick:" + satinInfo.name);
        holder.setText(R.id.tv_nick, satinInfo.name);
        holder.setText(R.id.tv_create_time, satinInfo.created_at);
        holder.setText(R.id.tv_content, satinInfo.text);

        ImageView avatarView = holder.getView(R.id.iv_avatar);
        Glide.with(avatarView.getContext()).load(satinInfo.profile_image).apply(options).into(avatarView);
    }
}
