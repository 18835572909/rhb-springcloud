package com.rhb.scaffold.config;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.context.annotation.Configuration;

/**
 * @author: rhb
 * @date: 2023/11/6 17:49
 * @description:
 */
//@Configuration
//public class RabbitListenerConfig implements RabbitListenerConfigurer {
//
//    @Override
//    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
//        SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
//        endpoint.setQueueNames("myQueue1", "myQueue2");
//        endpoint.setConcurrency("2");
//        endpoint.setMessageListener(message -> {
//            System.out.println("收到消息" + new String(message.getBody()));
//        });
//        registrar.registerEndpoint(endpoint);
//    }
//
//}
