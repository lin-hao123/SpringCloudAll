package com.atguigu.springcloud.alibaba.test.thread;

class ShareData{
    private int number = 0;
//判断、干活、通知
    public synchronized void increment() throws InterruptedException {
        //wait方法必须用在循环里，不能用if，否则会出现虚假唤醒
        while (number != 0){
            this.wait();
        }
        ++number;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        this.notifyAll();
    }

    public synchronized void decrement() throws InterruptedException {
        while (number==0){
            this.wait();
        }
        --number;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        this.notifyAll();
    }
}
/**
 * 题目：两个线程对一个初始值为零的变量操作，实现一个线程加一，另一个线程减一，来10轮
 * 口诀：线程操作资源类，高内聚（功能一定在资源类），低耦合（调用该资源的方法）
 */
public class ThreadDemo2 {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(()->{
            for (int i=0;i<10;i++){
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(()->{
            for (int i=0;i<10;i++){
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();
    }
}
