package cn.yeagle.common.mvp;

import cn.yeagle.common.http.IRepositoryManager;
import cn.yeagle.common.mvp.*;

/**
 *
 */
public class BaseModel implements cn.yeagle.common.mvp.IModel {
    protected IRepositoryManager mRepositoryManager;

    public BaseModel(IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }

    @Override
    public void onDestroy() {
        this.mRepositoryManager = null;
    }
}
