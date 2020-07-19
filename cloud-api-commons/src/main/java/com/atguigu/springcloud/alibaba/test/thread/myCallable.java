package com.atguigu.springcloud.alibaba.test.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**Callable有返回值，主要用于节省main线程的时间
 * 所以，FutureTask的get方法一般放在最后一行
 */
public class myCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        Thread.sleep(4000);
        System.out.println("******* come in call method");
        return 1024;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<>(new myCallable());
        new Thread(futureTask,"A").start();
        int a=5;
        System.out.println("a1: "+a);
//        System.out.println(futureTask.get());
        int b=5+futureTask.get();
        System.out.println("b: "+b);
        System.out.println("a: "+a);
    }
}
