package com.gz.rhb.web.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.gz.rhb.web.pojo.Item;
import com.gz.rhb.web.pojo.Order;
import org.springframework.stereotype.Service;
import com.gz.rhb.web.service.ItemService;
import com.gz.rhb.web.service.OrderService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: rhb
 * @date: 2023/10/25 20:35
 * @description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    private List<Order> orders = new ArrayList<>(4);

    private Map<String,List<Order>> userOrderMap = new HashMap<>(4);

    private Map<String,Order> orderMap = new HashMap<>(4);

    @Resource
    ItemService itemService;

    @PostConstruct
    private void init(){
        orders.add(Order.builder().addTime(DateUtil.date()).id("1000001").orderNo("20231025000001").orderPrice(new BigDecimal(23.5)).build());
        orders.add(Order.builder().addTime(DateUtil.date()).id("1000002").orderNo("20231025000002").orderPrice(new BigDecimal(20.5)).build());
        orders.add(Order.builder().addTime(DateUtil.date()).id("1000003").orderNo("20231025000003").orderPrice(new BigDecimal(15.5)).build());

        userOrderMap.put("007",Lists.newArrayList(orders.get(0),orders.get(1)));
        userOrderMap.put("008",Lists.newArrayList(orders.get(2),orders.get(1)));
        userOrderMap.put("009",Lists.newArrayList(orders.get(0),orders.get(2)));

        orderMap = orders.stream().collect(Collectors.toMap(Order::getOrderNo, Function.identity(), (k1, k2) -> k1));
    }

    @Override
    public Order getOrder(String orderNo) {
        List<Item> items = itemService.orderItemList(orderNo);
        Order order = orderMap.get(orderNo);
        order.setItems(items);
        return order;
    }

    @Override
    public List<Order> userOrderList(String userId) {
        return userOrderMap.get(userId);
    }
}
