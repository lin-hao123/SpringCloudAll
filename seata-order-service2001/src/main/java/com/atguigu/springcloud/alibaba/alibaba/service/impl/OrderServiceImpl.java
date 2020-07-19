package com.atguigu.springcloud.alibaba.alibaba.service.impl;

import com.atguigu.springcloud.alibaba.alibaba.dao.OrderDao;
import com.atguigu.springcloud.alibaba.alibaba.domain.Order;
import com.atguigu.springcloud.alibaba.alibaba.service.AccountService;
import com.atguigu.springcloud.alibaba.alibaba.service.OrderService;
import com.atguigu.springcloud.alibaba.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderDao orderDao;

    @Resource
    private AccountService accountService;

    @Resource
    private StorageService storageService;

    /**
     * 下订单-->减库存-->减余额-->修改订单状态
     * @param order
     */
    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("------>开始创建订单");
        orderDao.create(order);

        log.info("------>订单微服务开始调用库存，做扣减Count");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("------>订单微服务开始调用库存，做扣减End");

        log.info("------>订单微服务开始调用账户，做扣减Money");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("------>订单微服务开始调用账户，做扣减End");

        log.info("------>修改订单状态开始");
        orderDao.update(order.getUserId(),0);
        log.info("------>修改订单状态结束");

        log.info("------>订单已完成");
    }
}
