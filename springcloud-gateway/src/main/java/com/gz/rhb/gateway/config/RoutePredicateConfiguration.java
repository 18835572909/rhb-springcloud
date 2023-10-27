package com.gz.rhb.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: rhb
 * @date: 2023/10/26 15:27
 * @description: Bean方式的路由配置
 */
@Slf4j
@Configuration
public class RoutePredicateConfiguration {

    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        // 第一个参数是路由的唯一id
        routes
//            .route("order-service",r -> r.path("/order/**").uri("lb://web-service-2"))
            // curl -s -H 'service:item' localhost:30001/item/id/SNS000003
            .route("item-service",r -> r.header("service").uri("lb://web-service-3"))
            .route("item-service-1",r->r.header("token","123").uri("lb://web-service-3").filter(new CustomGatewayFilter()))
            .build();

        log.info("路由添加：path='/order/**',head='service: item'");
        return routes.build();
    }

}
