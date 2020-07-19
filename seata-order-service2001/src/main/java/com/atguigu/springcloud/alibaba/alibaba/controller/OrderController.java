package com.atguigu.springcloud.alibaba.alibaba.controller;

import com.atguigu.springcloud.alibaba.alibaba.domain.CommonResult;
import com.atguigu.springcloud.alibaba.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.alibaba.service.IdGeneratorSnowflake;
import com.atguigu.springcloud.alibaba.alibaba.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.awt.SunHints;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private IdGeneratorSnowflake idGeneratorSnowflake;

    @GetMapping(value = "/order/create")
    public CommonResult create(Order order){
        orderService.create(order);
        return new CommonResult(200,"订单创建成功");
    }

    /**
     * 生成id,通过雪花算法
     *
     * @return
     */
    @GetMapping("/snowflake")
    public String getIDBySnowflake() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            threadPool.submit(() -> {
                System.out.println(idGeneratorSnowflake.snowflakeId());
            });
        }
        threadPool.shutdown();
        return "hello snowflake";
    }
}
