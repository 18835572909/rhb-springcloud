package com.rhb.statemachine.statemachine.login;

import com.rhb.statemachine.pojo.Orders;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

/**
 * @author: rhb
 * @date: 2023/12/15 14:36
 * @description:
 */
public class LoginStatePersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {

    @Override
    public void onPersist(State<LoginState, LoginStateEvent> state, Message<LoginStateEvent> message, Transition<LoginState, LoginStateEvent> transition, StateMachine<LoginState, LoginStateEvent> stateMachine) {
//        if (message != null && message.getHeaders().containsKey("order")) {
//            Integer order = message.getHeaders().get("order", Integer.class);
//            Orders o = repo.findByOrderId(order);
//            OrderStatus status = state.getId();
//            o.setStatus(status);
//            repo.save(o);
//        }
    }

}
