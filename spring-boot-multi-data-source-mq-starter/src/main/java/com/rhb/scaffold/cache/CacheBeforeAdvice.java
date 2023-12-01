package com.rhb.scaffold.cache;

import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author: rhb
 * @date: 2023/11/23 18:36
 * @description:
 */
public class CacheBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(method.getName() + "before ... ");
    }


}
