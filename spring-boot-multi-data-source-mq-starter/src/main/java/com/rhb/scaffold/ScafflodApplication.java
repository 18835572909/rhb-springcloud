package com.rhb.scaffold;

import com.rhb.scaffold.annotation.EnableMultiRabbit;
import com.rhb.scaffold.util.RedisUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: rhb
 * @date: 2023/11/2 20:52
 * @description:
 */
@EnableMultiRabbit(isOpen = true,basePackages = "")
@SpringBootApplication
public class ScafflodApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = (AnnotationConfigApplicationContext)
                SpringApplication.run(ScafflodApplication.class, args);
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(RabbitListener.class);

        RedisUtils redisUtils = applicationContext.getBean(RedisUtils.class);
        redisUtils.delete("k1");

        System.out.println("启动成功");
    }

}
