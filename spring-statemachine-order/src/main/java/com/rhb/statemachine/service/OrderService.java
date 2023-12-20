package com.rhb.statemachine.service;

import com.rhb.statemachine.pojo.Orders;

import java.util.Map;

/**
 * @author: rhb
 * @date: 2023/12/14 15:57
 * @description:
 */
public interface OrderService {
    //创建新订单
    Orders create();
    //发起支付
    Orders pay(int id);
    //订单发货
    Orders deliver(int id);
    //订单收货
    Orders receive(int id);
    //获取所有订单信息
    Map<Integer, Orders> getOrders();
}
