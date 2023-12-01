package com.rhb.scaffold.cache;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @author: rhb
 * @date: 2023/11/23 11:07
 * @description:
 */
public class CacheAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = -9092594150238006012L;

    @Override
    public Pointcut getPointcut() {
        return new CachePointCut();
    }

    @Override
    public Advice getAdvice() {
        return new CacheBeforeAdvice();
    }

//    @Override
//    public Advice getAdvice() {
////        return new CacheBeforeAdvice();
//        return new CacheMethodIntercept();
//    }

}
