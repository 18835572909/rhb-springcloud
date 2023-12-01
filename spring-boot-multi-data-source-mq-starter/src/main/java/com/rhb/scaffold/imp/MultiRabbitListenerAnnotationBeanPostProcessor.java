package com.rhb.scaffold.imp;

import com.rhb.scaffold.util.AnnotationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: rhb
 * @date: 2023/11/7 14:41
 * @description:
 */
@Order(Integer.MIN_VALUE)
@Component
public class MultiRabbitListenerAnnotationBeanPostProcessor implements BeanPostProcessor,ApplicationContextAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);

        List<RabbitListener> collect = MergedAnnotations.from(targetClass, MergedAnnotations.SearchStrategy.TYPE_HIERARCHY)
                .stream(RabbitListener.class)
                .map(ann -> ann.synthesize())
                .collect(Collectors.toList());

        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(RabbitListener.class);
        beansWithAnnotation.forEach((k,v)->{
            Method[] methods = v.getClass().getMethods();
            for (Method m : methods){
                RabbitListener annotation = m.getAnnotation(RabbitListener.class);
                if(annotation!=null){
                    AnnotationUtils.setFieldValue(annotation,"containerFactory","simpleRabbitListenerContainerFactory2");
                }
            }
        });

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

//    private RabbitListenerAnnotationBeanPostProcessor.TypeMetadata buildMetadata(Class<?> targetClass) {
//        Collection<RabbitListener> classLevelListeners = findListenerAnnotations(targetClass);
//        final boolean hasClassLevelListeners = classLevelListeners.size() > 0;
//        final List<RabbitListenerAnnotationBeanPostProcessor.ListenerMethod> methods = new ArrayList<>();
//        final List<Method> multiMethods = new ArrayList<>();
//        ReflectionUtils.doWithMethods(targetClass, method -> {
//            Collection<RabbitListener> listenerAnnotations = findListenerAnnotations(method);
//            if (listenerAnnotations.size() > 0) {
//                methods.add(new RabbitListenerAnnotationBeanPostProcessor.ListenerMethod(method,
//                        listenerAnnotations.toArray(new RabbitListener[listenerAnnotations.size()])));
//            }
//            if (hasClassLevelListeners) {
//                RabbitHandler rabbitHandler = AnnotationUtils.findAnnotation(method, RabbitHandler.class);
//                if (rabbitHandler != null) {
//                    multiMethods.add(method);
//                }
//            }
//        }, ReflectionUtils.USER_DECLARED_METHODS);
//        if (methods.isEmpty() && multiMethods.isEmpty()) {
//            return RabbitListenerAnnotationBeanPostProcessor.TypeMetadata.EMPTY;
//        }
//        return new RabbitListenerAnnotationBeanPostProcessor.TypeMetadata(
//                methods.toArray(new RabbitListenerAnnotationBeanPostProcessor.ListenerMethod[methods.size()]),
//                multiMethods.toArray(new Method[multiMethods.size()]),
//                classLevelListeners.toArray(new RabbitListener[classLevelListeners.size()]));
//    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        this.beanFactory = beanFactory;
    }
}
