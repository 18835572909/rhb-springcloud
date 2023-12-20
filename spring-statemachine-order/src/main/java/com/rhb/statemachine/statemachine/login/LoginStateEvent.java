package com.rhb.statemachine.statemachine.login;

/**
 * @author: rhb
 * @date: 2023/12/14 21:15
 * @description:
 */
public enum LoginStateEvent {
    // 连接
    CONNECT,
    // 开始登录
    BEGIN_TO_LOGIN,
    // 登录成功
    LOGIN_SUCCESS,
    // 登录失败
    LOGIN_FAILURE,
    // 注销登录
    LOGOUT;
}
