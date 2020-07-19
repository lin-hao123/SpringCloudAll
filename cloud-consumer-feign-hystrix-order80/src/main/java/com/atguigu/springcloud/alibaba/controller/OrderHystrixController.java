package com.atguigu.springcloud.alibaba.controller;

import com.atguigu.springcloud.alibaba.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    /**
     * 服务降级，在消费端或服务端口可以做，一般在消费端做服务降级
     * execution.isolation.thread.timeoutInMilliseconds：程序出错或超时，都会服务降级fallback
     * 我们自己配置过的热部署方式对java代码的改动明显，
     * 但对@HystrixCommand内属性的修改建议重启微服务
     */
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentTimeOutFallbackMethod",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
//    })
    @HystrixCommand
    //添加注解@HystrixCommand,如果不指定服务降级，则会走默认的服务降级方法（类上添加注解@DefaultProperties）
    //如果没有添加注解@HystrixCommand,则需要做统一的fallback，与业务解耦的方式
    public String paymentInfo_Timeout(@PathVariable("id") Integer id){
//        int age = 10/0;
        String result = paymentHystrixService.paymentInfo_Timeout(id);
        return result;
    }
    public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
        return "我是消费者80,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己/(ㄒoㄒ)/~~";
    }

    /**
     * 全局fallback方法
     *
     * @return
     */
    private String payment_Global_FallbackMethod() {
        return "Global异常处理信息,请稍后再试。";
    }

}
