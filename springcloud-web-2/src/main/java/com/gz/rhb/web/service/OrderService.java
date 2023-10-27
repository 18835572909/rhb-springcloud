package com.gz.rhb.web.service;

import com.gz.rhb.web.pojo.Order;

import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/25 20:35
 * @description:
 */
public interface OrderService {

    Order getOrder(String orderNo);

    List<Order> userOrderList(String userId);

}
