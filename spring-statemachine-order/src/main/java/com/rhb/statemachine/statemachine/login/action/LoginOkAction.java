package com.rhb.statemachine.statemachine.login.action;

import com.rhb.statemachine.statemachine.login.LoginState;
import com.rhb.statemachine.statemachine.login.LoginStateEvent;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

/**
 * @author: rhb
 * @date: 2023/12/14 21:38
 * @description:
 */
public class LoginOkAction implements Action<LoginState,LoginStateEvent> {
    @Override
    public void execute(StateContext<LoginState, LoginStateEvent> context) {

    }
}
