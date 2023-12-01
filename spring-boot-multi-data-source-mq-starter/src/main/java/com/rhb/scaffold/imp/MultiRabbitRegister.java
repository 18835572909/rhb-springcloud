package com.rhb.scaffold.imp;

import com.rhb.scaffold.annotation.EnableMultiRabbit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: rhb
 * @date: 2023/11/2 21:46
 * @description:
 */
@Slf4j
public class MultiRabbitRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableMultiRabbit.class.getName()));
        String[] basePackages = annotationAttributes.getStringArray("basePackages");

        // 扫描固定类注入IOC容器： ClassPathBeanDefinitionScanner
        new MultiRabbitScanner(registry).doScan(basePackages);

        // 通过已经扫描的@MultiRabbitService中profile属性,获取相关配置文件中设置，填充RabbitMq相关容器，并注入Spring管理中【connectionFactory、listenerContainerFactory、rabbitTemplate、rabbitAdmin】
        /**
         * registry.registerBeanDefinition("",BeanDefinitionBuilder.genericBeanDefinition(SimpleRabbitListenerContainerFactory.class).getBeanDefinition());
         */




    }

}
