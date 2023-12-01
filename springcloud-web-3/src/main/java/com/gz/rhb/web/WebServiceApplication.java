package com.gz.rhb.web;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: rhb
 * @date: 2023/10/25 20:26
 * @description:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WebServiceApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(WebServiceApplication.class);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.run(args);
    }

}
