package cn.yeagle.common.task;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Future;

/**
 * Created by yeagle on 2018/3/2.
 */
public abstract class Task implements Runnable {
    public static final int STATE_IDLE = 0;
    public static final int STATE_RUNNING = 1;
    public static final int STATE_COMPLETE = 2;
    public static final int STATE_CANCEL = 3;

    private String taskName;
    private OnTaskListener taskListener;

    private Future<?> future;

    @State
    protected int _nState = STATE_IDLE;

    @IntDef({STATE_IDLE, STATE_RUNNING, STATE_COMPLETE, STATE_CANCEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Override
    public final void run() {
        try {
            _nState = STATE_RUNNING;
            doTask();
            _nState = STATE_COMPLETE;
        } catch (Exception e) {

        } finally {
            if (taskListener != null)
                taskListener.onFinish(this);

            future = null;
            taskListener = null;
        }
    }

    public void setOnTaskListener(OnTaskListener listener) {
        taskListener = listener;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void cancel() {
        if (future != null)
            future.cancel(true);

        _nState = STATE_CANCEL;
    }

    public Future<?> getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    public String getTaskName() {
        return this.taskName;
    }

    protected abstract void doTask();

    public interface OnTaskListener {
        public void onFinish(Task task);
    }
}
