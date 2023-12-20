package com.rhb.statemachine.statemachine.login.action;

import com.rhb.statemachine.statemachine.login.LoginState;
import com.rhb.statemachine.statemachine.login.LoginStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @author: rhb
 * @date: 2023/12/14 21:28
 * @description:
 */
@Slf4j
@Component
public class ConnectAction implements Action<LoginState,LoginStateEvent> {

    @Override
    public void execute(StateContext<LoginState, LoginStateEvent> context) {
      log.info("connect action:{}",context);
    }

}
