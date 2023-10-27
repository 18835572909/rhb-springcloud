package com.gz.rhb.gateway.config;

import com.alibaba.nacos.common.packagescan.resource.AntPathMatcher;
import com.alibaba.nacos.common.packagescan.util.PathMatcher;
import com.gz.rhb.gateway.config.bean.IgnoreWhiteProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author: rhb
 * @date: 2023/10/26 17:31
 * @description: 黑白名单过滤器
 */
@Component
public class BlackListUrlFilter extends AbstractGatewayFilterFactory<BlackListUrlFilter.Config> {

    public BlackListUrlFilter(){
        super(Config.class);
    }

    @Autowired
    IgnoreWhiteProperties ignoreWhiteProperties;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String url = exchange.getRequest().getURI().getPath();
            System.out.println("自定义的黑白名单局部过滤器");
            System.out.println(config.getBlacklistUrl());
            System.out.println("URL: "+url);
            // 跳过不需要验证的路径，即白名单
            PathMatcher pathMatcher = new AntPathMatcher();
            for (String s:ignoreWhiteProperties.getWhites()) {
                if (pathMatcher.match(s,url)) {
                    return chain.filter(exchange);
                }
            }
            if (config.matchBlacklist(url)) {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
                return exchange.getResponse().writeWith(
                        Mono.just(response.bufferFactory().wrap(ByteBuffer.wrap("请求地址不允许访问".getBytes()))));
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        private List<String> blacklistUrl;
        private final List<Pattern> blacklistUrlPattern = new ArrayList<>();
        public boolean matchBlacklist(String url) {
            return !blacklistUrlPattern.isEmpty() && blacklistUrlPattern.stream().anyMatch(p -> p.matcher(url).find());
        }
        public List<String> getBlacklistUrl() {
            return blacklistUrl;
        }
        public void setBlacklistUrl(List<String> blacklistUrl) {
            this.blacklistUrl = blacklistUrl;
            this.blacklistUrlPattern.clear();
            this.blacklistUrl.forEach(url -> {
                // 取消正则的贪婪模式
                this.blacklistUrlPattern.add(Pattern.compile(url.replaceAll("\\*\\*", "(.*?)"), Pattern.CASE_INSENSITIVE));
            });
        }
    }
}
