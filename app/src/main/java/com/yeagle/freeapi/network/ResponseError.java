package com.yeagle.freeapi.network;

import android.content.Context;

import javax.inject.Inject;

import cn.yeagle.common.http.base.BaseResponseError;
import cn.yeagle.common.utils.ToastUtil;

/**
 */
public class ResponseError extends BaseResponseError {

    @Inject
    public ResponseError() {}

    @Override
    protected String handleOtherError(Context context, Throwable t) {
        if (t instanceof ApiException) {
            ApiException exception = (ApiException)t;
            showErrorMsg(exception.getMessage());
            return exception.getMessage();
        }

        return null;
    }

    @Override
    protected void showErrorMsg(String msg) {
        ToastUtil.showToast(msg);
    }
}
