package com.yeagle.freeapi.home.adapter;

import com.yeagle.freeapi.R;
import com.yeagle.freeapi.home.model.SatinInfo;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 */
public class VoiceItemDelegate extends BaseSatinItemDelegate {
    public VoiceItemDelegate() {
        super(SatinInfo.TYPE_VOICE);
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_satin_voice;
    }

    @Override
    public void convert(ViewHolder holder, SatinInfo o, int position) {
        super.convert(holder, o, position);
    }
}
