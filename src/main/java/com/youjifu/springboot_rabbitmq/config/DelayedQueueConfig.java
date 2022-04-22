package com.youjifu.springboot_rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/21 20:36
 * 延迟队列
 */
@Configuration
public class DelayedQueueConfig {

    //交换机名称
    public static final String DELAY_EXCHANGE_NAME = "delay.exchange";
    //routingKey
    public static final String DELAY_ROUTING_KEY = "delay.routing_key";
    //队列名称
    public static final String DELAY_QUEUE_NAME = "delay.queue";

    @Bean("delayExchange")
    //声明交换机,自定义类型(延迟交换机)
    public CustomExchange delayExchange(){
        Map<String, Object> arguments = new HashMap<>();
        //设置延迟交换机的类型
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message",true,false,arguments);
    }


    @Bean("delayQueue")
    //声明队列
    public Queue delayQueue(){
        return new Queue(DELAY_QUEUE_NAME);
    }

    //绑定RoutingKey
    //最后调用构建方法noargs()
    @Bean
    public Binding bindingDelayedQueue(@Qualifier("delayQueue") Queue delayQueue,
                                       @Qualifier("delayExchange") CustomExchange delayExchange ){
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_ROUTING_KEY).noargs();
    }
}
