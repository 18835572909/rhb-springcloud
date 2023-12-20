package com.rhb.statemachine.statemachine.order;

import com.rhb.statemachine.pojo.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: rhb
 * @date: 2023/12/14 15:49
 * @description: 所以用户在使用spring stateMachine中的注解@WithStateMachine 的时候要切记，这个方法只是一种监听器行为，而不会控制状态的流转。
 *
 * 简而言之： 这里没有状态强判断，单纯的是listener的功能，即便报错，也会正常执行
 */
@Slf4j
@Component
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListener {

    @OnTransition(source = "WAIT_PAYMENT",target = "WAIT_DELIVER")
    public boolean payTransition(Message<OrderStateEvent> msg){
        Orders order = (Orders)msg.getHeaders().get("order");
        order = Optional.ofNullable(order).orElse(new Orders());
        order.setState(OrderState.WAIT_DELIVER);
        log.info("支付，状态机反馈信息：{}\r\n",order.getState());
        return true;
    }

    @OnTransition(source = "WAIT_DELIVER",target = "WAIT_RECEIVE")
    public boolean deliverTransition(Message<OrderStateEvent> msg){
        Orders order = (Orders)msg.getHeaders().get("order");
        order = Optional.ofNullable(order).orElse(new Orders());
        order.setState(OrderState.WAIT_RECEIVE);
        log.info("发货，状态机反馈信息：{}\r\n",order.getState());
        return true;
    }

    @OnTransition(source = "WAIT_RECEIVE",target = "FINISH")
    public boolean receiveTransition(Message<OrderStateEvent> msg){
        Orders order = (Orders)msg.getHeaders().get("order");
        order = Optional.ofNullable(order).orElse(new Orders());
        order.setState(OrderState.FINISH);
        log.info("收货，状态机反馈信息：{}\r\n",order.getState());
        return true;
    }

}
