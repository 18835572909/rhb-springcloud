package com.rhb.statemachine.statemachine.login;

/**
 * @author: rhb
 * @date: 2023/12/14 21:14
 * @description:
 */
public enum LoginState {
    // 未连接
    UNCONNECTED,
    // 已连接
    CONNECTED,
    // 正在登录
    LOGINING,
    // 登录失败
    LOGIN_FAIL,
    // 登录进系统
    LOGIN_INTO_SYSTEM,
    // 网络异常
    SYS_ERROR
    ;
}
