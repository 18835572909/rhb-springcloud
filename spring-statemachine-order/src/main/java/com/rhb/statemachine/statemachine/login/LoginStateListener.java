package com.rhb.statemachine.statemachine.login;

import cn.hutool.core.exceptions.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author: rhb
 * @date: 2023/12/15 14:00
 * @description: 状态机监听器
 */
@Slf4j
@Component
public class LoginStateListener extends StateMachineListenerAdapter<LoginState,LoginStateEvent> {

    @Override
    public void stateChanged(State<LoginState, LoginStateEvent> from, State<LoginState, LoginStateEvent> to) {
        log.info("[状态改变]from->to : {}->{}",Optional.ofNullable(from).orElse(null),Optional.ofNullable(to).orElse(null));

        String logInfo = "stateChanged" +
                " from " + (null != from ? from.getId().name() : null) +
                " to " + (null != to ? to.getId().name() : null);
        log.info(logInfo);
    }

    @Override
    public void stateEntered(State<LoginState, LoginStateEvent> state) {
        log.info("[进入状态]state : {}",Optional.ofNullable(state).orElse(null));
    }

    @Override
    public void stateExited(State<LoginState, LoginStateEvent> state) {
        log.info("[离开状态]state : {}",Optional.ofNullable(state).orElse(null));
    }

    @Override
    public void eventNotAccepted(Message<LoginStateEvent> event) {//
        log.info("[事件无法响应]event : {}",Optional.ofNullable(event).orElse(null));
    }

    @Override
    public void transition(Transition<LoginState, LoginStateEvent> transition) {
        log.info("[转换]transition : {}",Optional.ofNullable(transition).orElse(null));

        String logInfo = "transition" +
                " kind " + (null != transition.getKind() ? transition.getKind().name() : null) +
                " from " + (null != transition.getSource() ? transition.getSource().getId().name() : null) +
                " to " + (null != transition.getTarget() ? transition.getTarget().getId().name() : null) +
                " trigger " + (null != transition.getTrigger() ? transition.getTrigger().getEvent().name() : null);
        log.info(logInfo);
    }

    @Override
    public void transitionStarted(Transition<LoginState, LoginStateEvent> transition) {
        log.info("[转换开始]transition : {}",Optional.ofNullable(transition).orElse(null));
    }

    @Override
    public void transitionEnded(Transition<LoginState, LoginStateEvent> transition) {
        log.info("[转换结束]transition : {}",Optional.ofNullable(transition).orElse(null));
    }

    @Override
    public void stateMachineStarted(StateMachine<LoginState, LoginStateEvent> stateMachine) {
        log.info("[状态机启动]transition : {}",Optional.ofNullable(stateMachine).orElse(null));
    }

    @Override
    public void stateMachineStopped(StateMachine<LoginState, LoginStateEvent> stateMachine) {
        log.info("[状态机关闭]transition : {}",Optional.ofNullable(stateMachine).orElse(null));
    }

    @Override
    public void stateMachineError(StateMachine<LoginState, LoginStateEvent> stateMachine, Exception exception) {
        log.info("[状态机异常]transition : {}\r\nerror:{}",Optional.ofNullable(stateMachine).orElse(null),ExceptionUtil.getMessage(exception));
    }

    @Override
    public void extendedStateChanged(Object key, Object value) {
        log.info("[extendedStateChanged]key : {}  value:{}",key,value);
    }

    @Override
    public void stateContext(StateContext<LoginState, LoginStateEvent> stateContext) {
        log.info("[stateContext]stateContext : {}",stateContext);
    }
}
