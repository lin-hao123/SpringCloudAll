package com.atguigu.springcloud.alibaba.test.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ArrayList非线程安全，如何解决(注意：别答加锁)
 *
 * 1、Vector() 性能不高，适用于数据量少的情况
 * 2、Collections.synchronizedList(new ArrayList())  性能不高，适用于数据量少的情况
 * 3、CopyOnWriteArrayList()  底层采用读写分离，性能高，可用于高并发
 *
 * 写时复制:
 * CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，而是先将当前容器object[]进行copy,
 * 复制出一个新的容器object[] newElements, 然后新的容器object[] newElements 里添加元素，添加完元素之后，
 * 再将原容器的引用指向新的容器setArray(newElements); 这样做的好处是可以对CopyOnWrite容器进行并发的读，
 * 而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite 容器也是一种读写分离的思想，读和写不同的容器
 */
public class ListNoSafe {
    public static void main(String[] args) {
        List list = new CopyOnWriteArrayList();//Collections.synchronizedList(new ArrayList());//Vector();//ArrayList();
        for (int i=0;i<20;i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }
}
