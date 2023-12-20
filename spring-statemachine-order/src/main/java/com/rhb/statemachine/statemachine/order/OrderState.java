package com.rhb.statemachine.statemachine.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: rhb
 * @date: 2023/12/14 15:24
 * @description:
 */
@Getter
@AllArgsConstructor
public enum  OrderState {

    WAIT_PAYMENT("待支付"),
    WAIT_DELIVER("待发货"),
    WAIT_RECEIVE("待收货"),
    FINISH("完成");

    private String desc;
}
