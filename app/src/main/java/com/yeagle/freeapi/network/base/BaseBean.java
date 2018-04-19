package com.yeagle.freeapi.network.base;

/**
 * Created by xc_office on 2017/11/22.
 */
public class BaseBean<T>  { //Serializable比较方便 implements Parcelable，Parcelable更加有利于有优势

    protected static final int SUCCESS_CODE = 1000;

    private int    code;
    private String msg;
    private T      data;

    public void setStatus_code(int status_code) {
        this.code = status_code;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public int getStatus_code() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public boolean isOk() {
        return code == SUCCESS_CODE;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
