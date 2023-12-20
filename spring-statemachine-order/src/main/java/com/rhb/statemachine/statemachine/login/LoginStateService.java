package com.rhb.statemachine.statemachine.login;

import com.rhb.statemachine.statemachine.login.LoginStateEvent;
import com.rhb.statemachine.statemachine.login.PersistStateMachineHandler;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author: rhb
 * @date: 2023/12/15 15:12
 * @description:
 */
public class LoginStateService {

    private PersistStateMachineHandler handler;


    public LoginStateService(PersistStateMachineHandler handler) {
        this.handler = handler;
    }

//    @Autowired
//    private OrderRepo repo;
//
//    public String listDbEntries() {
//        List<LoginStateService> orders = repo.findAll();
//        StringJoiner sj = new StringJoiner(",");
//        for (Order order : orders) {
//            sj.add(order.toString());
//        }
//        return sj.toString();
//    }


    public boolean change(int order, LoginStateEvent event) {
//        Order o = repo.findByOrderId(order);
        return handler.handleEventWithState(MessageBuilder.withPayload(event).setHeader("order", order).build(), o.getStatus());
    }

}
