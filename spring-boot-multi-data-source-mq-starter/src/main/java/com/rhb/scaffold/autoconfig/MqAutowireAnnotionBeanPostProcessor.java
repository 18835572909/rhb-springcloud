package com.rhb.scaffold.autoconfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author: rhb
 * @date: 2023/11/1 16:36
 * @description:
 */
public class MqAutowireAnnotionBeanPostProcessor extends AutowiredAnnotationBeanPostProcessor {

    public MqAutowireAnnotionBeanPostProcessor() {
        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(4);
        autowiredAnnotationTypes.add(Autowired.class);
        autowiredAnnotationTypes.add(Value.class);
        try {
            Class<? extends Annotation> clz = (Class<? extends Annotation>) ClassUtils.forName("javax.inject.Inject", AutowiredAnnotationBeanPostProcessor.class.getClassLoader());
            autowiredAnnotationTypes.add(clz);
            logger.trace("JSR-330 'javax.inject.Inject' annotation found and supported for autowiring");
        }
        catch (ClassNotFoundException ex) {
            // JSR-330 API not available - simply skip.
        }
        setAutowiredAnnotationTypes(autowiredAnnotationTypes);
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 实例化Bean之前：InstantiationAwareBeanPostProcessor.postProcessBeforeInitialization()[AbstractAutowireCapableBeanFactory#doCreateBean]
        return super.postProcessBeforeInitialization(bean, beanName);
    }
}
