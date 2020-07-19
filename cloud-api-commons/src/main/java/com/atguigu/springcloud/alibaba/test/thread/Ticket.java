package com.atguigu.springcloud.alibaba.test.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//线程操纵资源，多线程一定是only one资源

/**
 * 题目：三个窗口售票
 */
public class Ticket {
    private int num = 30;
    Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();
        try {
            if (num > 0) {
                System.out.println(Thread.currentThread().getName() + " 余票： " + num--);
            }
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args){
        Ticket ticket = new Ticket();

        new Thread(()->{for (int i=0;i<40;i++)ticket.sale();},"A").start();
        new Thread(()->{for (int i=0;i<40;i++)ticket.sale();},"B").start();
        new Thread(()->{for (int i=0;i<40;i++)ticket.sale();},"C").start();

    }
}
