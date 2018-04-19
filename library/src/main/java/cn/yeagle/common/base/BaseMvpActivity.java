package cn.yeagle.common.base;

import javax.inject.Inject;

import cn.yeagle.common.base.*;
import cn.yeagle.common.mvp.IPresenter;

/**
 *  mvp 结构的activity
 * */
public abstract class BaseMvpActivity<P extends IPresenter> extends cn.yeagle.common.base.BaseActivity {
    @Inject
    protected P mPresenter;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }
}
