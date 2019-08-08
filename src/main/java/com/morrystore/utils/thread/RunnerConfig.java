package com.morrystore.utils.thread;

import java.util.List;

/**
 * 多线程运行配置
 * 
 * @param <T>
 */
public interface RunnerConfig<T> {

    /**
     * 拉取数据线程每5秒检测是否需要拉取新的数据
     */
    static final long DEFAULT_PULL_THREAD_SLEEP = 5000;

    /**
     * 在没有处理任务的情况下，任务线程每2秒检测是否有新的任务
     */
    static final long DEFAULT_TASK_THREAD_SLEEP = 2000;

    /**
     * 获取下一组任务数据
     * 
     * @return 返回用于线程处理的数据
     */
    List<T> next();

    /**
     * 处理数据 <br />
     * 如果使用到全局变量，应保证该方法的线程安全（sync）
     * 
     * @param item 数据元素
     */
    void process(T item);

    /**
     * 取数据线程休眠间隔（单位：毫秒）
     * 
     * @return 休眠毫秒数
     */
    default long pullThreadSleepTime() {
        return DEFAULT_PULL_THREAD_SLEEP;
    }

    /**
     * 任务线程休眠间隔（单位：毫秒）
     * 
     * @return 休眠毫秒数
     */
    default long taskThreadSleepTime() {
        return DEFAULT_TASK_THREAD_SLEEP;
    }
}