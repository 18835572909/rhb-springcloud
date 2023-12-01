package com.rhb.scaffold;

import com.rhb.scaffold.consumer.OrderConsumer;
import org.junit.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Map;

/**
 * @author: rhb
 * @date: 2023/11/6 16:32
 * @description:
 */
public class SampleTest {

    @Test
    public void invokeAnnotationField() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException {

        OrderConsumer consumer = new OrderConsumer();

        Class<? extends OrderConsumer> clz = consumer.getClass();

        AnnotatedType[] annotatedInterfaces = clz.getAnnotatedInterfaces();

        Annotation[] annotations = clz.getAnnotations();

        Method onMessage = clz.getDeclaredMethod("onMessage");

        RabbitListener annotation = onMessage.getAnnotation(RabbitListener.class);

        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);

        Field field = invocationHandler.getClass().getDeclaredField("memberValues");

        field.setAccessible(true);

        Map memberValues = (Map)field.get(invocationHandler);

        memberValues.put("containerFactory","defaultContainerFactory");

        field.set(invocationHandler,memberValues);

        System.out.println("annotation="+annotation.containerFactory());

        consumer.onMessage();
    }

}
