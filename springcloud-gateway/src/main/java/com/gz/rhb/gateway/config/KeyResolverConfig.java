package com.gz.rhb.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: rhb
 * @date: 2023/10/26 18:21
 * @description:
 */
@Configuration
public class KeyResolverConfig implements KeyResolver{
//    /**
//     * 根据路径限流
//     */
//    @Bean
//    public KeyResolver pathKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getURI().getPath());
//    }
//    /**
//     * 根据参数限流
//     */
//    //@Bean
//    public KeyResolver parameterKeyResolver() {
//        return exchange ->
//                Mono.just(exchange.getRequest().getQueryParams().getFirst("userId"));
//    }
//    /**
//     * 根据 IP 限流
//     */
//    //@Bean
//    public KeyResolver ipKeyResolver() {
//        return exchange ->
//                Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
//    }

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getURI().getPath());
    }
}

