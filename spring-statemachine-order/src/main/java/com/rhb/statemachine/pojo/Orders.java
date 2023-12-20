package com.rhb.statemachine.pojo;

import com.rhb.statemachine.statemachine.order.OrderState;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: rhb
 * @date: 2023/12/14 15:44
 * @description:
 */
@Data
public class Orders {

    private Integer id;

    private LocalDateTime createTime;

    private LocalDateTime lastTime;

    private OrderState state;

}
