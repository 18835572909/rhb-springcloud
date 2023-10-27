package com.gz.rhb.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: rhb
 * @date: 2023/10/25 18:34
 * @description:
 */
@RefreshScope
//@RestController
@RequestMapping("/test")
public class DemoController {

    @Value("${spring.cloud.gateway.routes[0].id}")
    private String a;

    // curl -H 'Accept: application/json' localhost:30001/test/1
    @GetMapping("/1")
    public String getProperties(){
        return a;
    }

}
