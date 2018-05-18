package cn.yeagle.common.adapter;

import android.content.Context;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by yeagle on 2018/5/7.
 */
public abstract class BaseRcAdapter<T> extends CommonAdapter<T> {
//    protected RequestOptions options = new RequestOptions().dontAnimate().placeholder(R.drawable.default_picture).priority(Priority.HIGH);

    public BaseRcAdapter(Context context, int layoutId, List<T> datas) {
        super(context, layoutId, datas);
    }

    public void addData(List<T> data) {
        if (mDatas != null)
            mDatas.addAll(data);
        else {
            mDatas = data;
        }

        notifyDataSetChanged();
    }

    protected RequestOptions createNewOption (int defaultResId) {
        return new RequestOptions().dontAnimate().placeholder(defaultResId).priority(Priority.HIGH);
    }

    public void setData(List<T> data) {
//        mDatas = data; // 父类跟父父类都定义了一个成员变量mDatas，其实父类那个成员变量没点用，导致这样直接赋值不能刷新只能这样操作才可以
        mDatas.clear();
        addData(data);
        notifyDataSetChanged();
    }
}
