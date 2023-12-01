package com.rhb.scaffold.cache;

import com.rhb.scaffold.util.RedisUtils;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * @author: rhb
 * @date: 2023/11/23 11:05
 * @description:
 */
public class CachePointCut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return "delete".equals(method.getName());
    }

    @Override
    public ClassFilter getClassFilter() {
        return new CacheClassFilter();
    }

    private static class CacheClassFilter implements ClassFilter{

        @Override
        public boolean matches(Class<?> clazz) {
            return RedisUtils.class .equals(clazz);
        }
    }

}
