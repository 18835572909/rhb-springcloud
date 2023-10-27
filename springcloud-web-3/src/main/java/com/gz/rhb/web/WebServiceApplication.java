package com.gz.rhb.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: rhb
 * @date: 2023/10/25 20:26
 * @description:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebServiceApplication.class,args);
    }

}
