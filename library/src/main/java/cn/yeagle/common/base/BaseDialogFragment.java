package cn.yeagle.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.yeagle.common.R;

/**
 * Created by yeagle on 2015/8/10.
 */
public abstract class BaseDialogFragment extends DialogFragment {
    protected final String TAG = this.getClass().getSimpleName();

    protected Unbinder mUnBinder;
    protected View mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, getStyle());
    }

    public int getStyle() {
        return R.style.DialogStyle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.setGravity(getGravity());

        window.setWindowAnimations(getIOAnimation());
        setFullWidth(window);

        View rootView = inflater.inflate(getLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(this, rootView);
        mRootView = rootView;
        initViews();
        return rootView;
    }

    protected void initViews() {

    }
    protected abstract int getLayoutId();

    private void setFullWidth(Window window) {
        if (!needFullWidth())
            return;

        WindowManager.LayoutParams params = window.getAttributes();
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        params.x = calWidth();//dm.widthPixels;
        window.setAttributes(params);
    }

    protected int calWidth() {
        return -1;
    }

    protected boolean needFullWidth() {
        return true;
    }

    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    protected int getIOAnimation() {
        return R.style.VerSlideAnimation;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mUnBinder != null)
            mUnBinder.unbind();
    }
}
