package com.atguigu.springcloud.alibaba.service;

import ch.qos.logback.core.util.TimeUtil;
import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import sun.awt.SunHints;

import javax.naming.Name;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id){
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_OK,id: "+id+"\t"+"O(∩_∩)O哈哈";
    }

    /**
     * 服务降级，在消费端或服务端口可以做，一般在消费端做服务降级
     * execution.isolation.thread.timeoutInMilliseconds：程序出错或超时，都会服务降级fallback
     * 我们自己配置过的热部署方式对java代码的改动明显，
     * 但对@HystrixCommand内属性的修改建议重启微服务
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandle",commandProperties = {
            @HystrixProperty( name="execution.isolation.thread.timeoutInMilliseconds", value="5000")
    })
    public String paymentInfo_Timeout(Integer id){
//        int age = 10/0; //模拟系统运行异常
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+" id: "+id+"\t"+"O(∩_∩)O哈哈"+"，耗时(秒): 3";
    }

    public String paymentInfo_TimeoutHandle(Integer id){
        return "线程池："+Thread.currentThread().getName()+" 8001系统繁忙或运行报错，请稍后再试,id: "+id+"\t"+"/(ㄒoㄒ)/~~";
    }

    //====服务熔断

    /**
     * 在10秒窗口期中10次请求有6次是请求失败的,断路器将起作用
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期/时间范文
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("*****id不能是负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功,流水号:" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数,请稍后重试,o(╥﹏╥)o id:" + id;
    }
}
