package com.rhb.statemachine.statemachine.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: rhb
 * @date: 2023/12/14 15:26
 * @description:
 */
@Getter
@AllArgsConstructor
public enum OrderStateEvent {

    PAYED("支付"),
    DELIVERY("发货"),
    RECEIVED("收货");

    private String desc;

}
