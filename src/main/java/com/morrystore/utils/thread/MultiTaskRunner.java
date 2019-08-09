package com.morrystore.utils.thread;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;
import com.morrystore.utils.ArrayUtils;
import com.morrystore.utils.ThreadUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多任务多线程运行器（框架） <br>
 * 
 * eg: <br>
 * 
 *  new MultiTaskRunner<ObjectToProcess>(new MyRunnerConfig()) <br>
 *   .start("task name", 10)                      <br>
 *   .join();
 * 
 * 
 * @see RunnerConfig
 * 
 * @param <T>
 * @author morry
 * @since 0.1
 */
public class MultiTaskRunner<T> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 对于多个实例，使用同一个线程池来执行任务
     */
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    private CountDownLatch latch;

    private RunnerConfig<T> config;

    private ConcurrentLinkedQueue<T> queue = new ConcurrentLinkedQueue<T>();

    private boolean running = false;

    private List<T> dataCache = null;

    /**
     * 初始化框架<br/>
     * 
     * @see RunnerConfig
     * @param config 配置
     */
    public MultiTaskRunner(RunnerConfig<T> config) {
        if(config == null) {
            throw new IllegalArgumentException("The parameter config must not be null");
        }
        this.config = config;
    }

    /**
     * 开始执行
     * 
     * @param name     任务名称
     * @param poolSize 线程池大小
     * @return 当前实例
     */
    public MultiTaskRunner<T> start(String name, int poolSize) {
        if(poolSize <= 0 || poolSize >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("The poolSize must great than 0 and lower than " + Integer.MAX_VALUE);
        }
        try {
            if (!hasData()) {
                logger.info("Data not found for -> {}", name);

                return this;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }

        latch = new CountDownLatch(poolSize + 1);

        running = true;

        //启动取数据线程
        threadPool.execute(() -> {
            while (true) {
                if (!running) {
                    break;
                }
                ThreadUtils.sleep(config.pullThreadSleepTime());
                if (queue.isEmpty()) {
                    try {

                        List<T> list = null;

                        //由于在 hasData() 中已经取过一次数据,所以这里需要判断,避免重复取数据
                        if(ArrayUtils.isNotEmpty(dataCache)) {
                            list = Lists.newArrayList();
                            list.addAll(dataCache);
                            // 清空临时缓存
                            dataCache.clear();
                        } else {
                            list = this.config.next();
                        }

                        if (ArrayUtils.isEmpty(list)) {
                            break;
                        } else {
                            for (T item : list) {
                                queue.offer(item);
                            }
                        }
                    } catch (Exception e) {
                        logger.info("Error when get next data : {}", e.getMessage());
                        break;
                    }
                }
            }
            latch.countDown();

            //停止所有任务
            stop();

            logger.info("Download [{}] was completed.", name);
        });

        //启动任务线程
        for (int i = 0; i < poolSize; i++) {
            threadPool.execute(new Task());
        }

        return this;
    }

    /**
     * 结束当前所有任务 <br>
     * 但任务不会立即停止
     */
    public void stop() {
        this.running = false;
    }

    /**
     * 将任务添加到调用线程中，等待所有任务执行完成返回
     * 
     * @throws InterruptedException
     */
    public void join() throws InterruptedException {
        if (running) {
            latch.await();
        }
    }

    /**
     * 判断是否有数据需要处理
     * @return
     */
    private boolean hasData() throws Exception {
        dataCache = this.config.next();
        return ArrayUtils.isNotEmpty(dataCache);
    }



    class Task implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (!running) {
                    break;
                }
                T item = queue.poll();
                if (item != null) {
                    try {
                        config.process(item);
                    } catch(Exception e) {
                        logger.error("Error in task : " ,e.getMessage() );
                    }
                } else {
                    ThreadUtils.sleep(config.taskThreadSleepTime());
                }
            }
            latch.countDown();
        }

    }
}