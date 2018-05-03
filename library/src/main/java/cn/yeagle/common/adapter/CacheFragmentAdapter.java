package cn.yeagle.common.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 */
public class CacheFragmentAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private static final String TAG = CacheFragmentAdapter.class.getSimpleName();

    private List<Bundle> mBundles;
    private LinkedList<WeakReference<T>> mCacheFragment;
    private Class mClazz;

    public CacheFragmentAdapter(FragmentManager fm, List<Bundle> bundles, Class clazz) {
        super(fm);
        this.mBundles = bundles;
        mCacheFragment = new LinkedList<WeakReference<T>>();
        this.mClazz = clazz;
    }

    @Override
    public Fragment getItem(int position) {
        if (mCacheFragment.size() > 1) { // 多创建一个，免得会出现问题
            T fragment = mCacheFragment.poll().get();
            if (fragment != null) {
                Bundle bundle = fragment.getArguments();
                bundle.clear();
                bundle.putAll(mBundles.get(position));

                return fragment;
            }
        }

        /**
         * 不改变mBundles里面原始的值
         */
        Bundle bundle = new Bundle(mBundles.get(position));
        try {
            T t = (T)mClazz.newInstance();
            t.setArguments(bundle);

            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getCount() {
        return mBundles.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

        mCacheFragment.offer(new WeakReference<>((T)object)); // fragment 缓存起来
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
