package com.atguigu.springcloud.alibaba.test.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Ticket2{
    private int num = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    public void increase() throws InterruptedException {
        lock.lock();
        try {
            while (num != 0){
                condition.await();
            }
            System.out.println(Thread.currentThread().getName()+"\t"+(++num));
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void decrease() throws InterruptedException {
        lock.lock();
        try {
            while (num == 0){
                condition.await();
            }
            System.out.println(Thread.currentThread().getName()+"\t"+(--num));
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
public class ThreadDemo {
    public static void main(String[] args) throws Exception{
        Ticket2 ticket = new Ticket2();
        new Thread(()->{
             try {
                 Thread.sleep(100);
                for (int i=1; i<=10; i++)
                ticket.increase();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();
        new Thread(()->{
            try {
                Thread.sleep(100);
                for (int i=1; i<=10; i++)
                ticket.decrease();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"B").start();
        new Thread(()->{
            try {
                Thread.sleep(100);
                for (int i=1; i<=10; i++)
                ticket.increase();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"C").start();
        new Thread(()->{
            try {
                Thread.sleep(100);
                for (int i=1; i<=10; i++)
                ticket.decrease();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"D").start();
    }
}
