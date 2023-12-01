package com.rhb.scaffold.imp;

import com.rhb.scaffold.annotation.MultiRabbitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author: rhb
 * @date: 2023/11/2 21:53
 * @description:
 */
@Slf4j
public class MultiRabbitScanner extends ClassPathBeanDefinitionScanner {

    public MultiRabbitScanner(BeanDefinitionRegistry registry) {
        super(registry,false);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(MultiRabbitService.class));
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder bdh : beanDefinitionHolders){
            BeanDefinition bd = bdh.getBeanDefinition();
            String beanClassName = bd.getBeanClassName();
            if(StringUtils.isEmpty(beanClassName)) continue;
            log.trace("[MultiRabbitService]注入：{}",beanClassName);

            try {
                Class<?> clz = ClassUtils.forName(beanClassName, this.getClass().getClassLoader());
                MultiRabbitService annotation = clz.getAnnotation(MultiRabbitService.class);


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return super.doScan(basePackages);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        if(metadata.isIndependent() || metadata.isInterface()){
            return true;
        }
        return super.isCandidateComponent(beanDefinition);
    }

}
