package com.yeagle.freeapi.network.base;

/**
 *
 */
public class BaseBean<T>  { //Serializable比较方便 implements Parcelable，Parcelable更加有利于有优势

    protected static final int SUCCESS_CODE = 200;

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

    @Override
    public String toString() {
        return data.toString();
    }
}
