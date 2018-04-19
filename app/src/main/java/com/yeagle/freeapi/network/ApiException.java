package com.yeagle.freeapi.network;

/**
 * Created by xc_office on 2017/11/22.
 * api返回非200会抛出该异常
 */
public class ApiException extends RuntimeException {
    private int errorCode;

    public ApiException(int code, String msg) {
        super(msg);
        this.errorCode = code;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
