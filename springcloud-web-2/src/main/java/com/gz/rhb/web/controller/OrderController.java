package com.gz.rhb.web.controller;

import com.gz.rhb.web.api.OrderApi;
import com.gz.rhb.web.pojo.Order;
import org.springframework.web.bind.annotation.RestController;
import com.gz.rhb.web.service.OrderService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/25 20:32
 * @description:
 */
@RestController
public class OrderController implements OrderApi {

    @Resource
    OrderService orderService;

    @Override
    public Order getOrder(String orderNo) {
        return orderService.getOrder(orderNo);
    }

    @Override
    public List<Order> userOrderList(String userId) {
        return orderService.userOrderList(userId);
    }
}
