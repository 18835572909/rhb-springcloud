package com.rhb.statemachine.statemachine.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachine;

/**
 * @author: rhb
 * @date: 2023/12/15 14:35
 * @description:
 */
public class PersistHandlerConfig {


    @Autowired
    private StateMachine<LoginState, LoginStateEvent> stateMachine;


    @Bean
    public LoginStateService persist() {
        PersistStateMachineHandler handler = persistStateMachineHandler();
        handler.addPersistStateChangeListener(persistStateChangeListener());
        return new LoginStateService(handler);
    }

    @Bean
    public PersistStateMachineHandler persistStateMachineHandler() {
        return new PersistStateMachineHandler(stateMachine);
    }

    @Bean
    public LoginStatePersistStateChangeListener persistStateChangeListener(){
        return new LoginStatePersistStateChangeListener();
    }

}
