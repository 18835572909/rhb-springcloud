package com.rhb.scaffold.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @author: rhb
 * @date: 2023/11/1 17:30
 * @description:
 */
@Data
@Slf4j
public class MultiSourceMqConfig {

    @Value("${mq.rabbitmq.host}")
    private String mq_host;
    @Value("${mq.rabbitmq.port}")
    private String mq_port;
    @Value("${mq.rabbitmq.username}")
    private String mq_name;
    @Value("${mq.rabbitmq.password}")
    private String mq_password;
    @Value("${mq.rabbitmq.vhost}")
    private String mq_vhost;

    @Autowired
    private RabbitProperties properties;

    @Bean(name="mqConnectFactory")
    @Primary
    public ConnectionFactory mqConnectFactory() {
        int port = Integer.valueOf(mq_host);
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(mq_host);
        connectionFactory.setUsername(mq_name);
        connectionFactory.setPassword(mq_password);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(mq_vhost);
        // 设置心跳检测
        connectionFactory.setRequestedHeartBeat(1000);
        // 设置发布消息后回调
        connectionFactory.setPublisherReturns(true);
        //SIMPLE,同步确认    CORRELATED,异步确认    NONE;不开启
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }

    @Bean("mqRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory mqRabbitListenerContainerFactory(@Qualifier("mqConnectFactory") ConnectionFactory connectionFactory,
                                                                                  SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        //消息序列化方式
        container.setMessageConverter(new Jackson2JsonMessageConverter());
        container.setErrorHandler(errorHandler());

        // 设置重连MQ的策略（每30s重连一次）
        FixedBackOff recoveryBackOff = new FixedBackOff(30000, FixedBackOff.UNLIMITED_ATTEMPTS);
        container.setRecoveryBackOff(recoveryBackOff);

        // 并发消费者数量
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(20);
        // 预加载消息数量 -- QOS
        container.setPrefetchCount(1);
        // 应答模式（此处设置为手动）
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        // 设置通知调用链 （这里设置的是重试机制的调用链）
        container.setAdviceChain(
                RetryInterceptorBuilder
                        .stateless()
                        .recoverer(new RejectAndDontRequeueRecoverer())
                        .retryOperations(rabbitRetryTemplate())
                        .build()
        );

        configurer.configure(container, connectionFactory);

        return container;
    }

    @Bean("mqRabbitTemplate")
    public RabbitTemplate mqRabbitTemplate(@Qualifier("mqConnectFactory") ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        // 序列化message
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        //此处设置重试template后，会再生产者发送消息的时候，调用该template中的调用链
        rabbitTemplate.setRetryTemplate(rabbitRetryTemplate());

        /**
         * mandatory：交换器无法根据自身类型和路由键找到一个符合条件的队列时的处理方式
         *          true：RabbitMQ会调用Basic.Return命令将消息返回给生产者
         *          false：RabbitMQ会把消息直接丢弃
         */
        rabbitTemplate.setMandatory(true);

        // 设置confirm回调函数
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("传递消息到交换机成功,correlationData:{}, cause:{}", correlationData.getId(), cause);
            } else {
                log.error("传递消息到交换机失败,correlationData:{}, cause:{}", correlationData.getId(), cause);
            }
        });

        // 设置
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String msg = new String(message.getBody());
            log.error("消息{}不能被正确路由，routingKey为{},exchange:{}", msg, routingKey,exchange);
        });

        return rabbitTemplate;
    }

    @Bean(name="mqAmqpAdmin")
    public AmqpAdmin amqpAdmin(@Qualifier("mqConnectFactory") ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(false);
        return admin;
    }

    @Bean
    public RetryTemplate rabbitRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        // 设置监听  调用重试处理过程
        retryTemplate.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
                // 执行之前调用 （返回false时会终止执行）
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                // 重试结束的时候调用 （最后一次重试 ）
                System.out.println("---------------最后一次调用");

                return ;
            }
            @Override
            public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                //  异常 都会调用
                System.err.println("-----第{}次调用"+retryContext.getRetryCount());
            }
        });
        retryTemplate.setBackOffPolicy(backOffPolicyByProperties());
        retryTemplate.setRetryPolicy(retryPolicyByProperties());
        return retryTemplate;
    }

    @Bean
    public ExponentialBackOffPolicy backOffPolicyByProperties() {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        long maxInterval = properties.getListener().getSimple().getRetry().getMaxInterval().getSeconds();
        long initialInterval = properties.getListener().getSimple().getRetry().getInitialInterval().getSeconds();
        double multiplier = properties.getListener().getSimple().getRetry().getMultiplier();
        // 重试间隔
        backOffPolicy.setInitialInterval(initialInterval * 1000);
        // 重试最大间隔
        backOffPolicy.setMaxInterval(maxInterval * 1000);
        // 重试间隔乘法策略
        backOffPolicy.setMultiplier(multiplier);
        return backOffPolicy;
    }

    @Bean
    public SimpleRetryPolicy retryPolicyByProperties() {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        int maxAttempts = properties.getListener().getSimple().getRetry().getMaxAttempts();
        retryPolicy.setMaxAttempts(maxAttempts);
        return retryPolicy;
    }

    @Bean
    public ConditionalRejectingErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(new ConditionalRejectingErrorHandler.DefaultExceptionStrategy());
    }

}
