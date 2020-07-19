package com.atguigu.springcloud.alibaba.alibaba.dao;

import com.atguigu.springcloud.alibaba.alibaba.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
    /**
     * 1 新建订单
     * @param order
     * @return
     */
    void create(Order order);

    /**
     * 2 修改订单状态,从0改为1
     * @param userId
     * @param status
     * @return
     */
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
