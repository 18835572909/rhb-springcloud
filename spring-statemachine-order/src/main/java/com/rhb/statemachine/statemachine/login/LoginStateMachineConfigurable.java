package com.rhb.statemachine.statemachine.login;

import com.rhb.statemachine.statemachine.login.action.ConnectAction;
import com.rhb.statemachine.statemachine.login.action.LoginFailAction;
import com.rhb.statemachine.statemachine.login.action.LoginOkAction;
import com.rhb.statemachine.statemachine.login.guard.ConnectGuard;
import com.rhb.statemachine.statemachine.login.guard.LoginFailGuard;
import com.rhb.statemachine.statemachine.login.guard.LoginOkGuard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import javax.annotation.Resource;
import java.util.EnumSet;

/**
 * @author: rhb
 * @date: 2023/12/14 21:19
 * @description:
 */
@Slf4j
@EnableStateMachine(name = "loginStateMachine")
public class LoginStateMachineConfigurable extends StateMachineConfigurerAdapter<LoginState,LoginStateEvent> {

    @Resource
    ConnectGuard connectGuard;
    @Resource
    ConnectAction connectAction;
    @Resource
    LoginOkGuard loginOkGuard;
    @Resource
    LoginOkAction loginOkAction;
    @Resource
    LoginFailGuard loginFailGuard;
    @Resource
    LoginFailAction loginFailAction;

    @Override
    public void configure(StateMachineStateConfigurer<LoginState, LoginStateEvent> states) throws Exception {
        states.withStates()
                .initial(LoginState.UNCONNECTED)
                .states(EnumSet.allOf(LoginState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<LoginState, LoginStateEvent> transitions) throws Exception {
        transitions
                /**
                 * >> withExternal 是当source和target不同时的写法，比如付款成功后状态发生的变化。
                 * withExternal的source和target用于执行前后状态、event为触发的事件、guard判断是否执行action。同时满足source、target、event、guard的条件后执行最后的action
                 */
                .withExternal()
                    .source(LoginState.UNCONNECTED)
                    .event(LoginStateEvent.CONNECT)
                    .target(LoginState.CONNECTED)
                    .guard(connectGuard)
                    .action(connectAction)
                .and()
                .withExternal()
                    .source(LoginState.CONNECTED)
                    .event(LoginStateEvent.BEGIN_TO_LOGIN)
                    .target(LoginState.LOGINING)
                .and()
                .withExternal()
                    .source(LoginState.LOGIN_INTO_SYSTEM)
                    .event(LoginStateEvent.LOGOUT)
                    .target(LoginState.LOGINING)
                .and()
                /**
                 * withChoice 当执行一个动作，可能导致多种结果时，可以选择使用choice+guard来跳转
                 * withChoice 根据guard的判断结果执行first/then的逻辑。
                 * withChoice 不需要发送事件来进行触发
                 */
                .withChoice()
                    .source(LoginState.LOGINING)
                    .first(LoginState.LOGIN_INTO_SYSTEM,loginOkGuard,loginOkAction)
                    .then(LoginState.LOGIN_FAIL,loginFailGuard,loginFailAction)
                    .last(LoginState.SYS_ERROR)
                .and()
                /**
                 * withInternal 当source和target相同时的串联写法，比如付款失败后都是待付款状态。
                 */
                .withInternal()
                    .source(LoginState.LOGIN_FAIL)
                    .event(LoginStateEvent.LOGIN_FAILURE);
        ;
    }


}
