package com.yeagle.freeapi.home.adapter;

import com.yeagle.freeapi.R;
import com.yeagle.freeapi.home.model.SatinInfo;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by yeagle on 2018/5/9.
 */
public class TextItemDelegate extends BaseSatinItemDelegate {
    public TextItemDelegate() {
        super(SatinInfo.TYPE_TEXT);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_satin_text;
    }

    @Override
    public void convert(ViewHolder holder, SatinInfo o, int position) {
        super.convert(holder, o, position);
    }
}
