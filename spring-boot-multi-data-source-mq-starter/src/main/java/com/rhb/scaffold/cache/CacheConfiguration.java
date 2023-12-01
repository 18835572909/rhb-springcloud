package com.rhb.scaffold.cache;

import org.springframework.aop.Advisor;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: rhb
 * @date: 2023/11/23 18:37
 * @description:
 */
@Configuration
public class CacheConfiguration {

    @Bean
    public Advisor cacheAdvisor(){
        return new CacheAdvisor();
    }

    @Bean
    public Advisor cachePointcutAdvisor(){
        CachePointcutAdvisor cachePointcutAdvisor = new CachePointcutAdvisor();
        cachePointcutAdvisor.setAdvice(new CacheInterceptor());
        return cachePointcutAdvisor;
    }

}
