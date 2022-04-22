package com.youjifu.springboot_rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/22 14:57
 * 发布确认 (高级)
 */
@Configuration
public class ConfirmConfig {
    //交换机名称
    public static final String EXCHANGE_NAME = "confirm.exchange";
    //队列名称
    public static final String QUEUE_NAME = "confirm.queue";
    public static final String ROUTING_KEY = "key1";
    //备份交换机和队列
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    public static final String WARNING_QUEUE_NAME = "warning.queue";

    @Bean("confirmQueue")
    //声明队列
    public Queue confirmQueue(){
        return QueueBuilder.durable(QUEUE_NAME).build();
    }
    //声明交换机
    @Bean("confirmExchange")
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(EXCHANGE_NAME).durable(true)
                .withArgument("alternate-exchange",BACKUP_EXCHANGE_NAME).build();
    }

    //绑定routingKey
    @Bean
    public Binding queueBingExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                     @Qualifier("confirmExchange") DirectExchange confirmExchange){
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(ROUTING_KEY);
    }

    //----------------------------备份交换机-------------------------------------
    // 声明备份队列
    @Bean("backQueue")
    public Queue backQueue(){
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    // 声明警告队列
    @Bean("warningQueue")
    public Queue warningQueue(){
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    //声明备份交换机
    @Bean("backupExchange")
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    // 声明报警队列绑定关系
    @Bean
    public Binding warningBinding(@Qualifier("warningQueue") Queue queue,
                                  @Qualifier("backupExchange") FanoutExchange
                                          backupExchange){
        return BindingBuilder.bind(queue).to(backupExchange);
    }

    // 声明备份队列绑定关系
    @Bean
    public Binding backupBinding(@Qualifier("backQueue") Queue queue,
                                 @Qualifier("backupExchange") FanoutExchange backupExchange){
        return BindingBuilder.bind(queue).to(backupExchange);
    }

}
