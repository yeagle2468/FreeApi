package com.yeagle.freeapi.novel.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.BaseRcAdapter;
import com.yeagle.freeapi.novel.model.NovelInfo;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by yeagle on 2018/5/7.
 */
public class NovelAdapter extends BaseRcAdapter<NovelInfo> {
    public NovelAdapter(Context context, List<NovelInfo> datas) {
        super(context, R.layout.rc_item_novel, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NovelInfo novelInfo, int position) {
        holder.setText(R.id.tv_title, novelInfo.bookname);
        holder.setText(R.id.tv_introduction, novelInfo.introduction);

        ImageView imageView = holder.getView(R.id.iv_cover);
        Glide.with(mContext)
                .load(novelInfo.book_cover)
                .apply(options)
                .into(imageView);
    }
}
