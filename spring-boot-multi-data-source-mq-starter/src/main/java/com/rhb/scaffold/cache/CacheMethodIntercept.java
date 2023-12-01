package com.rhb.scaffold.cache;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author: rhb
 * @date: 2023/11/23 18:38
 * @description:
 */
public class CacheMethodIntercept implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(method.getName() + "intercept ... ");
        return true;
    }

}
