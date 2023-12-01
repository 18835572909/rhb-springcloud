package com.rhb.scaffold.consumer;

import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.lang.reflect.Method;

/**
 * @author: rhb
 * @date: 2023/11/6 16:43
 * @description:
 */
@RabbitListener(id = "orderService")
public class OrderConsumer {

    @SneakyThrows
    @RabbitListener(containerFactory = "simpleContainerFactory",id = "orderHandler")
    public void onMessage(){
        Method onMessage = this.getClass().getMethod("onMessage");
        RabbitListener annotation = onMessage.getAnnotation(RabbitListener.class);
        String containerFactory = annotation.containerFactory();
        System.out.println("containerFactory="+containerFactory);
    }

}
