package com.youjifu.springboot_rabbitmq.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/21 13:26
 * TTL队列(延迟队列),配置文件类的代码
 */
@Configuration
public class TtlQueueConfig {
    //普通交换机名称
    private  static final  String X_NORMAL_EXCHANGE = "X";
    //死信交换机名称
    private  static final  String Y_DEAD_EXCHANGE = "Y";
    //普通队列名称
    public  static  final  String QA_NORMAL_QUEUE =  "QA";
    public  static  final  String QB_NORMAL_QUEUE =  "QB";
    public  static  final  String QC_NORMAL_QUEUE =  "QC";
    //死信队列名称
    public  static  final  String QD_DEAD_QUEUE =  "QD";

    //声明交换机X,交换机类型为直接交换机(direct)
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_NORMAL_EXCHANGE);
    }
    //声明交换机Y
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(Y_DEAD_EXCHANGE);
    }

    //声明普通队列QA
    @Bean("qaQueue")
    public Queue qaQueue(){
        Map<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "YD");
        //设置TTl时间单位是10ms
        arguments.put("x-message-ttl", 10000);
                                    //QA队列持久化
        return QueueBuilder.durable(QA_NORMAL_QUEUE).withArguments(arguments).build();
    }

    //声明普通队列QB
    @Bean("qbQueue")
    public Queue qbQueue(){
        Map<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "YD");
        //设置TTl时间单位是40ms
        arguments.put("x-message-ttl", 40000);
                                    //QB队列持久化
        return QueueBuilder.durable(QB_NORMAL_QUEUE).withArguments(arguments).build();
    }
    //声明普通队列QC
    @Bean("qcQueue")
    public Queue qcQueue(){
        Map<String, Object> arguments = new HashMap<>(3);
        //设置死信交换机
        arguments.put("x-dead-letter-exchange", Y_DEAD_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "YD");
                                    //QC队列持久化
        return QueueBuilder.durable(QC_NORMAL_QUEUE).withArguments(arguments).build();
    }
    //声明死信队列QD
    @Bean("qdQueue")
    public Queue qdQueue(){
        return QueueBuilder.durable(QD_DEAD_QUEUE).build();
    }
    // 绑定QA_routingKey
    @Bean
    public Binding queueQaBindingX(@Qualifier("qaQueue") Queue qaQueue,
                                   @Qualifier("xExchange")DirectExchange xExchange){
        return BindingBuilder.bind(qaQueue).to(xExchange).with("XA");
    }
    // 绑定QB_routingKey
    @Bean
    public Binding queueQbBindingX(@Qualifier("qbQueue")Queue qbQueue,
                                   @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(qbQueue).to(xExchange).with("XB");
    }
    // 绑定QC_routingKey
    @Bean
    public Binding queueQcBindingX(@Qualifier("qcQueue")Queue qcQueue,
                                   @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(qcQueue).to(xExchange).with("XC");
    }
    // 绑定QD_routingKey
    @Bean
    public Binding queueQdBindingX(@Qualifier("qdQueue")Queue qdQueue,
                                   @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(qdQueue).to(yExchange).with("YD");
    }

}
