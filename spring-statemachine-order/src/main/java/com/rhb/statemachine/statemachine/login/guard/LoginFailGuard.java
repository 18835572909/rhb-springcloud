package com.rhb.statemachine.statemachine.login.guard;

import com.rhb.statemachine.statemachine.login.LoginState;
import com.rhb.statemachine.statemachine.login.LoginStateEvent;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

/**
 * @author: rhb
 * @date: 2023/12/14 21:38
 * @description:
 */
public class LoginFailGuard implements Guard<LoginState,LoginStateEvent> {

    @Override
    public boolean evaluate(StateContext context) {
        return false;
    }

}
