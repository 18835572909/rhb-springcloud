package com.rhb.statemachine.statemachine.login.guard;

import com.rhb.statemachine.statemachine.login.LoginState;
import com.rhb.statemachine.statemachine.login.LoginStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

/**
 * @author: rhb
 * @date: 2023/12/14 21:25
 * @description:
 */
@Slf4j
@Component
public class ConnectGuard implements Guard<LoginState,LoginStateEvent> {

    @Override
    public boolean evaluate(StateContext<LoginState, LoginStateEvent> context) {
        log.info("");
        return true;
    }

}
