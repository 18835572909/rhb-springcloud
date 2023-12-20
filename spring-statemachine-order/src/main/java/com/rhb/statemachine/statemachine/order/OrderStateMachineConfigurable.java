package com.rhb.statemachine.statemachine.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

/**
 * @author: rhb
 * @date: 2023/12/14 15:27
 * @description:
 */
@Slf4j
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfigurable extends StateMachineConfigurerAdapter<OrderState,OrderStateEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderStateEvent> states) throws Exception {
        states.withStates()
                .initial(OrderState.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderStateEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderState.WAIT_PAYMENT).event(OrderStateEvent.PAYED).target(OrderState.WAIT_DELIVER)
                .and()
                .withExternal().source(OrderState.WAIT_DELIVER).event(OrderStateEvent.DELIVERY).target(OrderState.WAIT_RECEIVE)
                .and()
                .withExternal().source(OrderState.WAIT_RECEIVE).event(OrderStateEvent.RECEIVED).target(OrderState.FINISH);
    }

}
