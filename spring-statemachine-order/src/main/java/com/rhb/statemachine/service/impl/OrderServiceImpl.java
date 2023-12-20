package com.rhb.statemachine.service.impl;

import com.rhb.statemachine.pojo.Orders;
import com.rhb.statemachine.service.OrderService;
import com.rhb.statemachine.statemachine.order.OrderState;
import com.rhb.statemachine.statemachine.order.OrderStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: rhb
 * @date: 2023/12/14 15:57
 * @description:
 */
@Slf4j
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private StateMachine<OrderState, OrderStateEvent> orderStateMachine;

    private int id = 1;

    private Map<Integer, Orders> orders = new HashMap<>();

    @PostConstruct
    public void init(){
        orderStateMachine.start();
    }

    @PreDestroy
    public void destory(){
        orderStateMachine.stop();
    }

    @Override
    public Orders create() {
        Orders order = new Orders();
        order.setState(OrderState.WAIT_PAYMENT);
        order.setId(id++);
        order.setCreateTime(LocalDateTime.now());
        order.setLastTime(LocalDateTime.now());
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Orders pay(int id) {
        Orders order = orders.get(id);

        log.info("线程名称：{} 尝试支付，订单号：{} 订单状态：{}" ,Thread.currentThread().getName(),id,order.getState());

        if (!sendEvent(OrderStateEvent.PAYED, order)) {
            log.info("线程名称：" + Thread.currentThread().getName() + " 支付失败, 状态异常，订单号：" + id);
        }

        return orders.get(id);
    }

    @Override
    public Orders deliver(int id) {
        Orders order = orders.get(id);

        log.info("线程名称：{} 尝试发货，订单号：{} 订单状态：{}" ,Thread.currentThread().getName(),id,order.getState());

        if (!sendEvent(OrderStateEvent.DELIVERY, order)) {
            log.info("线程名称：" + Thread.currentThread().getName() + " 发货失败，状态异常，订单号：" + id);
        }

        return orders.get(id);
    }

    @Override
    public Orders receive(int id) {
        Orders order = orders.get(id);

        log.info("线程名称：{} 尝试收货，订单号：{} 订单状态：{}" ,Thread.currentThread().getName(),id,order.getState());

        if (!sendEvent(OrderStateEvent.RECEIVED, order)) {
            log.info("线程名称：" + Thread.currentThread().getName() + " 收货失败，状态异常，订单号：" + id);
        }

        return orders.get(id);
    }

    @Override
    public Map<Integer, Orders> getOrders() {
        return orders;
    }

    private synchronized boolean sendEvent(OrderStateEvent event, Orders order) {
        Message<OrderStateEvent> message = MessageBuilder.withPayload(event).setHeader("order", order).build();
        return orderStateMachine.sendEvent(message);
    }
}
