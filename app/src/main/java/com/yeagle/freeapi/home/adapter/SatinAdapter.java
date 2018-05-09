package com.yeagle.freeapi.home.adapter;

import android.content.Context;

import com.yeagle.freeapi.home.model.SatinInfo;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by yeagle on 2018/5/9.
 */
public class SatinAdapter extends MultiItemTypeAdapter<SatinInfo> {

    public SatinAdapter(Context context, List<SatinInfo> datas) {
        super(context, datas);
    }


}
