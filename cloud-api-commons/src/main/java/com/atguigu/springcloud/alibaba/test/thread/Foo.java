package com.atguigu.springcloud.alibaba.test.thread;

@FunctionalInterface
public interface Foo {
    //函数式接口只能有一个抽象的方法，否则需要去掉注解@FunctionalInterface
    public int add(int x,int y);
    //java8后允许有default接口实现，可以有多个
    public default int div(){
        return 10/3;
    }
    //静态实现也可以有多个，使用方式：类名.方法名
    public static int sub(){
        return 10-3;
    }
}

class Test1{
    public static void main(String[] args) {
        Foo foo = (x,y)->{
            return x+y;
        };
        System.out.println("add abstract method: "+foo.add(3,5));
        System.out.println("div default method: "+foo.div());
        System.out.println("sub static method: "+ Foo.sub());
    }
}
