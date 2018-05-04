package cn.yeagle.common.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cn.yeagle.common.R;
import cn.yeagle.common.base.BaseDialogFragment;
import cn.yeagle.common.utils.LogUtils;

/**
 * Created by yeagle on 2018/3/7.
 */
public class BottomListDialog extends BaseDialogFragment {
    public static final String ARGS = "args";

    private OnItemClickListener mListener;
    private int mBgColor = Color.WHITE, mDivider = Color.LTGRAY;
//    private int mItemTextColor = Color.GRAY, mCancelColor = Color.GRAY;

    public static BottomListDialog newInstance(String[] args, Parcelable info) {
        BottomListDialog f = new BottomListDialog();

        Bundle bundle = new Bundle();
        bundle.putStringArray(ARGS, args);
        bundle.putParcelable("info", info);
        f.setArguments(bundle);

        return f;
    }

    public void setInfo(Parcelable info) {
        Bundle bundle = getArguments();
        if (bundle != null)
            bundle.putParcelable("info", info);
    }

    /**
     *
     * @param bgColor 背景color
     * @param divider 对应list分割线的颜色
     */
    public void setListItemColor(int bgColor, int divider) {
        this.mBgColor = bgColor;
        this.mDivider = divider;
    }

    @Override
    protected void initViews() {
        Bundle bundle = getArguments();

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mRootView.setMinimumWidth(dm.widthPixels);

        ListView listView = mRootView.findViewById(R.id.list);
        listView.setBackgroundColor(mBgColor);
        listView.setDivider(new ColorDrawable(mDivider));

        if (bundle != null) {
            String[] args = bundle.getStringArray("args");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.lib_dialog_item_text , R.id.text, args);
            listView.setAdapter(adapter);

            LogUtils.e(TAG, "bundle != null");
            listView.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long id) -> {

                LogUtils.e(TAG, "setOnItemClickListener");
                Bundle arg = getArguments();
                Parcelable info = null;

                if (arg != null) {
                    info = arg.getParcelable("info");
                }
                if (mListener != null)
                    mListener.onClickItem(adapterView, view, position, info);

                if (itemNeedDismiss())
                    dismissAllowingStateLoss();
            });
        }

        View cancelView = mRootView.findViewById(R.id.cancel);
        cancelView.setOnClickListener((view) -> dismiss());
        cancelView.setBackgroundColor(mBgColor);
    }

    protected boolean itemNeedDismiss() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.lib_bottom_list_dialog_layout;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        public void onClickItem(ViewGroup container, View view, int position, Parcelable info);
    }
}
