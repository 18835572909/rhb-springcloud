package com.gz.rhb.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: rhb
 * @date: 2023/10/25 16:09
 * @description:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewaySentinelApplication {

    public static void main(String[] args) {
        System.setProperty("csp.sentinel.app.type", "1");
        SpringApplication.run(GatewaySentinelApplication.class,args);
    }

}
