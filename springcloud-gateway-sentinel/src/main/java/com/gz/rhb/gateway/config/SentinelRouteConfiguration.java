//package com.gz.rhb.gateway.config;
//
//import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
//import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
//import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
//import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.web.reactive.result.view.ViewResolver;
//
//import javax.annotation.PostConstruct;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @author: rhb
// * @date: 2023/10/27 14:35
// * @description: bean类型配置sentinel的限流规则
// */
//@Configuration  // 配置类
//public class SentinelRouteConfiguration {   // 路由限流规则配置类
//
//    private final List<ViewResolver> viewResolvers;
//
//    private final ServerCodecConfigurer serverCodecConfigurer;
//
//
//    public SentinelRouteConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer) {
//        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
//        this.serverCodecConfigurer = serverCodecConfigurer;
//    }
//
//    @PostConstruct
//    public void initGatewayRules() {    // 初始化限流规则
//        Set<GatewayFlowRule> rules = new HashSet<>();
//
//        GatewayFlowRule gatewayFlowRule = new GatewayFlowRule("user_route");    // 资源名(gateway中的路由id)
//        gatewayFlowRule.setCount(1);    // 限流阈值
//        gatewayFlowRule.setIntervalSec(1);  // 统计时间窗口，默认1s
//
//        rules.add(gatewayFlowRule);
//        GatewayRuleManager.loadRules(rules);    // 载入规则
//    }
//
//    @PostConstruct
//    public void initCustomizedApis() {
//        Set<ApiDefinition> apiDefinitions = new HashSet<>();
//
//        ApiDefinition apiDefinition = new ApiDefinition("order_api") // 定义 api分组
//                .setPredicateItems(new HashSet<ApiPredicateItem>() {{
//                    add(new ApiPathPredicateItem()
//                            .setPattern("/order/userOrders/**")	// 路径匹配规则
//                            .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX)); // 匹配策略，前缀匹配
//                }});
//
//        apiDefinitions.add(apiDefinition);
//        GatewayApiDefinitionManager.loadApiDefinitions(apiDefinitions); // 载入API分组定义
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public GlobalFilter sentinelGatewayFilter() {   // 初始化限流过滤器
//        return new SentinelGatewayFilter();
//    }
//
//}
//
