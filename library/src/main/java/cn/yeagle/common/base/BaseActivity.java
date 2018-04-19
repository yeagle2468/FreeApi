package cn.yeagle.common.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import java.util.LinkedList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.yeagle.common.model.FragmentInfo;
import cn.yeagle.common.mvp.BasePresenter;
import cn.yeagle.common.utils.ActivityUtils;
import cn.yeagle.common.utils.PhoneSysUtil;
import cn.yeagle.common.utils.StatusBarUtil;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.DaggerAppCompatActivity;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * 本身是继承{@link DaggerAppCompatActivity}这个，更改更通用的 {@link AppCompatActivity}
 * 再将rxrecycle加入进来
 *
 * // 这个可以达到深色的效果
 StatusBarUtil.statusBarLightMode(this);
 // 这个达到浅色的效果
 //        StatusBarUtil.transparencyBar(this);
 * 默认支持注解 {@link #useInject()} 不需要可以返回false，
 * 如果不需要又不在 {ActivityBindingModule} 类绑定的话，就会报错
 *
 * 根据阿里巴巴开发手册做以下调整：
 *  1、对于activity所有的fragment add、replace操作，都通过这个里面的{@link #addFragment(Fragment, String)}
 *  {@link #addFragment(Fragment, int, String)} )}
 *  {@link #replaceFragment(Fragment, int)}
 *
 *   优势：
 *  避免产生 IllegalStateExceptionStateLoss异常，同时不需要请求{@link android.support.v4.app.FragmentTransaction#commitAllowingStateLoss()}
 *  另外对重复fragment的过滤处理
 *
 *  2、手册有说明{@link #onDestroy()} 做一些释放操作可能会较晚，这里在 {@link #onStop()}里面 判断 {@link #isFinishing()}
 *     然后实现了一个 {@link #trash()}函数
 *     建议一些资源释放工作在这里处理
 *
 *  3、那里还强制了一点就是，在onPause或者onStop是关闭当前activity正在执行的动画
 */
public abstract class BaseActivity extends AppCompatActivity implements LifecycleProvider<ActivityEvent>, HasFragmentInjector, HasSupportFragmentInjector {
    protected final String TAG = getClass().getSimpleName();

    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<android.app.Fragment> frameworkFragmentInjector;

    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();

    protected View     mBackView;
    protected TextView mTitle;
    protected TextView mBackTv;
    protected Toolbar  mToolbar;

    protected Unbinder mBinder;

    protected boolean  hasSaveInstance;
    /**
     * 这些是属于 onSaveInstanceState之后显示的fragment
     */
    private LinkedList<FragmentInfo> mResumeFragments = new LinkedList<>();

    @Override
    protected void onCreate(Bundle bundle) {
        if (useInject())
            AndroidInjection.inject(this);
        super.onCreate(bundle);
        lifecycleSubject.onNext(ActivityEvent.CREATE);

        if (!isImmersiveStatusBar()) {
            //解决状态栏兼容性问题
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            //小米手机系统特殊处理
            if (PhoneSysUtil.isMIUI()) {
                StatusBarUtil.statusBarLightMode(this);
            }
        }

        final int layoutResID = getLayoutId();
        if (layoutResID > 0) {
            setContentView(layoutResID);
            //组件初始化
            mToolbar = findViewById(getToolbarId());
            //绑定控件
            mBinder = ButterKnife.bind(this);
            //初始化自定义标题栏
            initCustomTitleBar();
            //初始化标题栏
            initToolBar();
            //试图进一步初始化
            initViews(bundle);
        }
        StatusBarUtil.statusBarLightMode(this);
    }

    public boolean useInject() {
        return true;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return supportFragmentInjector;
    }

    @Override
    public AndroidInjector<android.app.Fragment> fragmentInjector() {
        return frameworkFragmentInjector;
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (mTitle != null) {
            mTitle.setText(title);
        }
    }

    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (mTitle != null) {
            mTitle.setText(titleId);
        }
    }

    public void setBackTitle(String name) {
        if (mBackTv != null)
            mBackTv.setText(name);
    }

    /**
     * 初始化views
     *
     * @param bundle
     */
    public void initViews(Bundle bundle) {

    }

    /**
     * 初始化定义标题栏(返回按钮和标题)
     */
    public void initCustomTitleBar() {
        mTitle = findViewById(getTitleId());
        mBackView = findViewById(getBackViewId());
        mBackTv = findViewById(getBackTvId());
        if (mBackView != null) { //初始化返回操作按钮
            mBackView.setVisibility(isBackViewVisible() ? View.VISIBLE : View.GONE);
            mBackView.setOnClickListener((v) -> onBackPressed());
        }

        if (mBackTv != null)
            mBackTv.setVisibility(isBackViewVisible() ? View.VISIBLE : View.GONE);
    }

    /**
     * 初始化toolbar
     */
    public void initToolBar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    /**
     * 获取根布局,默认情况下返回R.layout.activity_root，不需要覆盖
     * @return
     */
    protected abstract int getLayoutId();
    protected abstract int getToolbarId();
    protected abstract int getTitleId();
    protected abstract int getBackTvId();

    protected int getBackViewId() {
        return 0;
    }

    /**
     * 是否是沉浸式状态栏，默认不是
     *
     * @return
     */
    protected boolean isImmersiveStatusBar() {
        return false;
    }

    /**
     * 是都有返回键
     * @return
     */
    protected boolean isBackViewVisible() {
        return true;
    }

    @Override
    @CallSuper
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityEvent.START);
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(ActivityEvent.RESUME);
        hasSaveInstance = false;
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();

        if (isFinishing())
            trash();
    }

    /**
     * 也是根据阿里巴巴的手册说明，资源释放操作建议在这里
     */
    protected void trash() {

    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<ActivityEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        hasSaveInstance = true;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        if (mResumeFragments.isEmpty())
            return;

        // 这里来添加 fragment
        for (FragmentInfo info : mResumeFragments) {
            if (info.action == FragmentInfo.ACTION_ADD) {
                addFragmentInternal(info.fragment, info.id, info.tag);
            } else {
                ActivityUtils.replaceFragmentActivity(getSupportFragmentManager(), info.fragment, info.id);
            }
        }
        mResumeFragments.clear();
    }

    protected void addFragment(Fragment fragment, String tag) {
        if (!checkSaveInstance(fragment, tag, FragmentInfo.ACTION_ADD)) {
            addFragmentInternal(fragment, 0, tag);
        } // else 不做任何操作，下面已经做了
    }

    /**
     * tag 最好每个都带上，并且保证每个fragment类对应一个标签，主要用来查询用的
     * @param fragment
     * @param id
     * @param tag
     */
    protected void addFragment(Fragment fragment, int id, String tag) {
        if (!checkSaveInstance(fragment, id, tag, FragmentInfo.ACTION_ADD)) {
            addFragmentInternal(fragment, id, tag);
        } // else 不做任何操作，下面已经做了
    }

    private void addFragmentInternal(Fragment fragment, int id, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!isExistFragment(fragmentManager, tag)) {
            if (id > 0)
                ActivityUtils.addFragmentToActivity(fragmentManager, fragment, id, tag);
            else
                ActivityUtils.addFragmentToActivity(fragmentManager, fragment, tag);
        }
    }

    private boolean isExistFragment(FragmentManager fragmentManager, String tag) {
        Fragment existFragment = fragmentManager.findFragmentByTag(tag);
        if (existFragment == null)
            return false;

        return true;
    }

    protected void replaceFragment(Fragment fragment, int id) {
        if (!checkSaveInstance(fragment, id, null, FragmentInfo.ACTION_REPLACE)) {
            ActivityUtils.replaceFragmentActivity(getSupportFragmentManager(), fragment, id);
        } // else 不做任何操作，下面已经做了
    }

    private boolean checkSaveInstance(Fragment fragment, String tag, @FragmentInfo.ACTION int action) {
        return checkSaveInstance(fragment, 0, tag, action);
    }

    private boolean checkSaveInstance(Fragment fragment, int id, String tag, @FragmentInfo.ACTION int action) {
        if (hasSaveInstance) { // 已经saveInstanceState了
            mResumeFragments.add(new FragmentInfo(fragment, id, tag, action));
            return true;
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        lifecycleSubject.onNext(ActivityEvent.DESTROY);
        super.onDestroy();

        mResumeFragments.clear();
        if (mBinder != null) {
            mBinder.unbind();
        }
    }
}
