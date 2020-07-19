package com.atguigu.springcloud.alibaba.test.collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    private BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(3);
    public void throwsException(){
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("d"));
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.element());
    }

    public void particularValue(){
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));
        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.peek());
    }
    public void blocking() throws InterruptedException {
            blockingQueue.put("a");
            blockingQueue.put("b");
            blockingQueue.put("c");
//            blockingQueue.put("d");
            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();
    }

    public void overtime() throws InterruptedException {
        System.out.println(blockingQueue.offer("a",3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("a",3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(3L,TimeUnit.SECONDS));
    }
    public static void main(String[] args) throws InterruptedException {
        BlockingQueueDemo blockingQueueDemo = new BlockingQueueDemo();
//        blockingQueueDemo.throwsException();
//        blockingQueueDemo.particularValue();
//        blockingQueueDemo.blocking();
        blockingQueueDemo.overtime();
    }
}
