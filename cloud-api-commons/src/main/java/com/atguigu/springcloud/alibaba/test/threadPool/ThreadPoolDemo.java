package com.atguigu.springcloud.alibaba.test.threadPool;

import java.util.concurrent.*;

/**
 *Executors是操作线程池的工具类，相当于Collections和Arrays操作集合和数组一样
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {
        final int CPU_NUM =Runtime.getRuntime().availableProcessors(); //获取CPU数量
//        ExecutorService threadPool = Executors.newFixedThreadPool(5); //固定线程池
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();//单线程线程池
//        ExecutorService threadPool = Executors.newCachedThreadPool();//可拓展线程池，保证线程够用，保证有线程过来马上执行，
        ExecutorService threadPool = new ThreadPoolExecutor(   //自定义线程池
                2,
                CPU_NUM+1,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            for (int i=0; i<19; i++){
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+"\t 办理业务");
                });
//                TimeUnit.MILLISECONDS.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
