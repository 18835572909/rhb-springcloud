package com.gz.rhb.web.api;

import com.gz.rhb.web.pojo.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/25 21:31
 * @description: 订单服务
 */
@RequestMapping("/order")
public interface OrderApi {

    // curl -s -H 'Accept: application/json' localhost:10002/order/orderNo/20231025000002
    @GetMapping("/orderNo/{orderNo}")
    Order getOrder(@PathVariable("orderNo") String orderNo);

    // curl -s -H 'Accept: application/json' localhost:10002/order/userOrders/007
    @GetMapping("/userOrders/{userId}")
    List<Order> userOrderList(@PathVariable("userId") String userId);

}
