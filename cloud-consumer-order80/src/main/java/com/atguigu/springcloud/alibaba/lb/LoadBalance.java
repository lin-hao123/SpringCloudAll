package com.atguigu.springcloud.alibaba.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 自定义负载均衡轮询算法
 */
public interface LoadBalance {

    ServiceInstance instance(List<ServiceInstance> serviceInstances);
}
