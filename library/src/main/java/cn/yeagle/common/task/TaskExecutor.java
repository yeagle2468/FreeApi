package cn.yeagle.common.task;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yeagle on 2018/3/1.
 *  看了阿里开发手册，发现这个很有必要
 *  线程将从这里来取，禁止私自new Thread
 */
public class TaskExecutor {
    private static TaskExecutor mInstance;

    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private BlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<>();
    private ExecutorService executorService;

    private HashMap<String, Task> mTaskMap;

    private TaskExecutor() {
        executorService = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT,
                taskQueue, new DefaultThreadFactory());
        mTaskMap = new HashMap<>();
    }

    public static TaskExecutor getInstance() {
        return mInstance;
    }

    /**
     * 需要保证线程安全，这里不做处理
     * @return
     */
    public static TaskExecutor createInstance() {
        if (mInstance == null)
            mInstance = new TaskExecutor();

        return mInstance;
    }

    public void execute(@NonNull Runnable command) {
        executorService.execute(command);
    }

    @NonNull
    public Future<?> submit(@NonNull Runnable task) {
        Future<?> future =  executorService.submit(task);
        return future;
    }

    public Future<?> submitTask(@NonNull Task task) {
        return submitTask(task, false);
    }

    public Future<?> submitTask(@NonNull Task task, boolean filter) {
        if (filter) {
            String taskName = task.getTaskName();
            if (!TextUtils.isEmpty(taskName) && mTaskMap.containsKey(taskName)) {
                return task.getFuture();
            }

            // 以下都对过滤的进行处理
            synchronized (mTaskMap) {
                mTaskMap.put(taskName, task);
            }
            task.setOnTaskListener(mListener);
        }

        Future future = submit(task);
        task.setFuture(future);

        return future;
    }

    public synchronized void shutdown() {
        synchronized (mTaskMap) {
            mTaskMap.clear();
        }
        executorService.shutdown();
    }

    public synchronized List<Runnable> shutdownNow() {
        synchronized (mTaskMap) {
            mTaskMap.clear();
        }
        return executorService.shutdownNow();
    }

    private Task.OnTaskListener mListener = new Task.OnTaskListener() {
        @Override
        public void onFinish(Task task) {
            if (mTaskMap.containsKey(task.getTaskName()))
                mTaskMap.remove(task.getTaskName());
        }
    };

    private static final class DefaultThreadFactory implements ThreadFactory {
        private static final int DEFAULT_PRIORITY = android.os.Process.THREAD_PRIORITY_BACKGROUND
                + android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE;

//        @Synthetic final boolean preventNetworkOperations;
        private int threadNum;

        DefaultThreadFactory() { // boolean preventNetworkOperations
//            this.preventNetworkOperations = preventNetworkOperations;
        }

        @Override
        public synchronized Thread newThread(@NonNull Runnable runnable) {
            final Thread result = new Thread(runnable, "task-thread-" + threadNum) {
                @Override
                public void run() {
                    // why PMD suppression is needed: https://github.com/pmd/pmd/issues/808
                    android.os.Process.setThreadPriority(DEFAULT_PRIORITY); //NOPMD AccessorMethodGeneration
//                    if (preventNetworkOperations) {
//                        StrictMode.setThreadPolicy(
//                                new StrictMode.ThreadPolicy.Builder()
//                                        .detectNetwork()
//                                        .penaltyDeath()
//                                        .build());
//                    }
                    super.run();
                }
            };
            threadNum++;
            return result;
        }
    }
}
