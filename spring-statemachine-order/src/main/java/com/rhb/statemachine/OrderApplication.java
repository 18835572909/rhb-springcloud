package com.rhb.statemachine;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.rhb.statemachine.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author: rhb
 * @date: 2023/12/14 15:02
 * @description: 核心源码类：{@link org.springframework.statemachine.processor.StateMachineHandlerCallHelper}
 */
@Slf4j
@SpringBootApplication
public class OrderApplication implements CommandLineRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OrderApplication.class,args);

        OrderService orderService = (OrderService)context.getBean("orderService");
        orderService.create();
        orderService.pay(1);
        orderService.deliver(1);
        orderService.receive(1);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("OrderService Starting. Time:{}",DateUtil.now());
    }
}
