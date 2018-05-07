package com.yeagle.freeapi.home.adapter;

import android.content.Context;

import com.yeagle.freeapi.R;
import com.yeagle.freeapi.base.BaseRcAdapter;
import com.yeagle.freeapi.home.model.Sentence;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by yeagle on 2018/5/7.
 */
public class PersonSignAdapter extends BaseRcAdapter<Sentence> {
    public PersonSignAdapter(Context context, List<Sentence> datas) {
        super(context, R.layout.simple_textview_layout, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Sentence sentence, int position) {
        holder.setText(R.id.text, sentence.femalename);
    }
}
