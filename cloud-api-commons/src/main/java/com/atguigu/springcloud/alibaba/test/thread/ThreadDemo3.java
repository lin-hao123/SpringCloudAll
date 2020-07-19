package com.atguigu.springcloud.alibaba.test.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData3{
    private int num = 1;
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();
    public void print5(){
        lock.lock();
        try {
            while (num !=1){
                condition1.await();
            }
            for (int i=1; i<=5; i++)
            System.out.println(Thread.currentThread().getName()+"\t"+i);
            ++num;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print10(){
       lock.lock();
        try {
            while (num !=2){
                condition2.await();
            }
            for (int i=1; i<=10; i++)
                System.out.println(Thread.currentThread().getName()+"\t"+i);
                ++num;
                condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print15(){
        lock.lock();
        try {
            while (num !=3){
                condition3.await();
            }
            for (int i=1; i<=15; i++)
                System.out.println(Thread.currentThread().getName()+"\t"+i);
            num=1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
/**
 * 多线程之间按顺序调用，实现A->B->C
 * 三个线程启动，要求如下：
 * A打印5次，B打印10次，C打印15次
 * 接着
 * A打印5次，B打印10次，C打印15次
 * ......来10轮
 *
 * 1、高聚低合前提下，线程操作资源类
 * 2、判断，干活，通知
 * 3、多线程交互中，必须要防止多线程的虚假唤醒，也即（判断用while，不能用if）
 * 4、标志位
 */
public class ThreadDemo3 {
    public static void main(String[] args) {
        ShareData3 shareData = new ShareData3();
        new Thread(()->{
            for (int i=1; i<=10; i++){
                shareData.print5();
            }
        },"A").start();
        new Thread(()->{
            for (int i=1; i<=10; i++){
                shareData.print10();
            }
        },"B").start();
        new Thread(()->{
            for (int i=1; i<=10; i++){
                shareData.print15();
            }
        },"C").start();
    }
}
