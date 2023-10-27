package com.gz.rhb.gateway.config;

import com.alibaba.cloud.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: rhb
 * @date: 2023/10/26 18:04
 * @description:
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain
            chain) {
        //String url = exchange.getRequest().getURI().getPath();
        //忽略以下url请求
        //if(url.indexOf("/login") >= 0){
        // return chain.filter(exchange);
        // }
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (StringUtils.isBlank(token)) {
            logger.info( "token is empty ..." );
            ServerHttpResponse response = exchange.getResponse();
            // 响应类型
            response.getHeaders().add("Content-Type", "application/json; charset=utf-8");
            // 响应状态码，HTTP 401 错误代表用户没有访问权限
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 响应内容
            // 可以自定义全局返回类
            // String message = JSON.toJSONString(xxx);
            String message = "{\"message\":\"" + HttpStatus.UNAUTHORIZED.getReasonPhrase() + "\"}";
            DataBuffer buffer = response.bufferFactory().wrap(message.getBytes());
            return response.writeWith(Mono.just(buffer));
            // 也可以直接简单点返回，这样就没有返回消息
            // exchange.getResponse().setStatusCode( HttpStatus.UNAUTHORIZED );
            // return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return 0;
    }
}

