package com.gz.rhb.gateway.config.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/26 17:31
 * @description:
 */
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "ignore")
public class IgnoreWhiteProperties
{
    /**
     * 放行白名单配置，网关不校验此处的白名单
     */
    private List<String> whites = new ArrayList<>();
    public List<String> getWhites()
    {
        return whites;
    }
    public void setWhites(List<String> whites)
    {
        this.whites = whites;
    }
}
