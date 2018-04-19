package com.yeagle.freeapi.network.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xc_office on 2017/12/6.
 *
 * 如果新api标准返回中，你只想定义list数组中的item对象。  那么BaseRecyclerViewFragment的setRequestParams()设置解析参数类型时，
 *  你可以传入：
 *       TypeToken<BaseListBeanImp<ItemBean>> typeToken = new TypeToken<BaseListBeanImp<ItemBean>>() {};
 */

public class BaseListBean<T> {
    private ArrayList<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
}
