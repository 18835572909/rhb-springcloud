package com.rhb.scaffold.cache;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author: rhb
 * @date: 2023/11/23 18:41
 * @description:
 */
public class CachePointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return new CachePointCut();
    }
}
