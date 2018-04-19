package cn.yeagle.common.http.base;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import cn.yeagle.common.R;
import cn.yeagle.common.utils.HttpUtils;
import io.reactivex.exceptions.CompositeException;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import retrofit2.HttpException;

/**
 *  error 统一处理处
 */
public class BaseResponseError implements ResponseErrorListener {

    @Override
    public void handleResponseError(Context context, Throwable t) {
//        Timber.tag("Catch-Error").w(t.getMessage());
        //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
        if (t instanceof CompositeException) { // RxJava 很多都是这种exception CompositeException
            List<Throwable> list = ((CompositeException)t).getExceptions();

            if (list != null && list.size() > 0) // 暂且取第一个
                t = list.get(0);
        }

        doHandleResponseError(context, t);
    }

    /**
     * 这层过滤exception
     * @param context
     * @param t
     */
    private void doHandleResponseError(Context context, Throwable t) {
        int msgId = R.string.unknown_error;
        String msg = null;

        if (t instanceof UnknownHostException) {
            msgId = R.string.network_invalid_error;
        } else if (t instanceof SocketTimeoutException) {
            msgId = R.string.time_out_error;
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            msg = convertStatusCode(context, httpException);
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException || t instanceof JsonIOException) {
            msgId = R.string.parse_data_error;
        } else {
            if (handleOtherError(context, t) != null)
                return;
        }
        if (msg == null)
            msg = context.getString(msgId);
        showErrorMsg(msg);
        Log.e("RELL", "t:" + t.getClass().getSimpleName());
    }

    protected String handleOtherError(Context context, Throwable t) {
        return null;
    }

    protected void showErrorMsg(String msg) {

    }

    /**
     *  主要处理HTTP code
     * @param context
     * @param httpException
     * @return
     */
    private String convertStatusCode(Context context, HttpException httpException) {
        String msg = HttpUtils.getErrorMsg(context, "request_server_error_", httpException.code());
        if (msg != null) {
            return msg;
        } else {
            return httpException.message();
        }
    }
//    protected String convertServerCode(Context context, HttpException httpException)  {
//
//    }
}
