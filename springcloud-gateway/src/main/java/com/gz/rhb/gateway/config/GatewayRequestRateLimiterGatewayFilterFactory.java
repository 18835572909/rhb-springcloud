package com.gz.rhb.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: rhb
 * @date: 2023/10/26 21:29
 * @description: 重新封装RateLimit
 *
 * #使用原生的Redis限流。
 * filters:
 *   - name: RequestRateLimiter
 *     args:
 *       # 每秒处理多少个平均请求数
 *       redis-rate-limiter.replenishRate: 1
 *       # 令允许在一秒钟内完成的最大请求数
 *       redis-rate-limiter.burstCapacity: 1
 *       # 获取 Bean 对象，@Bean的标识，默认bean名称与方法名一样。
 *       key-resolver: "#{@uriKeyResolver}"
 */
@Slf4j
@Component
public class GatewayRequestRateLimiterGatewayFilterFactory extends RequestRateLimiterGatewayFilterFactory{

    private final RateLimiter defaultRateLimiter;

    private final KeyResolver defaultKeyResolver;

    public GatewayRequestRateLimiterGatewayFilterFactory(RateLimiter defaultRateLimiter, KeyResolver defaultKeyResolver, RateLimiter defaultRateLimiter1, KeyResolver defaultKeyResolver1) {
        super(defaultRateLimiter, defaultKeyResolver);
        this.defaultRateLimiter = defaultRateLimiter1;
        this.defaultKeyResolver = defaultKeyResolver1;
    }

    @Override
    public GatewayFilter apply(Config config) {
        KeyResolver resolver = getOrDefault(config.getKeyResolver(), defaultKeyResolver);

        RateLimiter<Object> limiter = getOrDefault(config.getRateLimiter(), defaultRateLimiter);
        return (exchange, chain) ->
                resolver.resolve(exchange)
                        .flatMap(key -> {
                            String routeId = config.getRouteId();
                            log.info("routeId:{}",routeId);
                            if (routeId == null) {
                                Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                                routeId = route.getId();
                            }

                            String finalRouteId = routeId;
                            return limiter
                                    .isAllowed(routeId, key)
                                    .flatMap(response -> {
                                        for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
                                            exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
                                        }
                                        if (response.isAllowed()) {
                                            return chain.filter(exchange);
                                        }
                                        log.warn("已限流: {}", finalRouteId);

                                        //修改code为500
                                        ServerHttpResponse httpResponse = exchange.getResponse();
                                        httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

                                        if (!httpResponse.getHeaders().containsKey("Content-Type")) {
                                            httpResponse.getHeaders().add("Content-Type", "application/json");
                                        }

                                        //此处无法触发全局异常处理，手动返回
                                        DataBuffer buffer = httpResponse.bufferFactory().wrap(("{\n"
                                                + "  \"code\": \"1414\","
                                                + "  \"message\": \"服务器限流\","
                                                + "  \"data\": \"Server throttling\","
                                                + "  \"success\": false"
                                                + "}").getBytes(StandardCharsets.UTF_8));
                                        return httpResponse.writeWith(Mono.just(buffer));
                                    });
                        });
    }

    private <T> T getOrDefault(T configValue, T defaultValue) {
        return configValue != null ? configValue : defaultValue;
    }
}
