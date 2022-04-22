package com.youjifu.springboot_rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/22 15:51
 *
 * 回调接口
 */
@Slf4j
@Component
public class MyCallBack  implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostConstruct()
    //注入到rabbitTemplate中
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    //交换机发生故障确认回调
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String reason) {
        String id = correlationData != null ? correlationData.getId() : "";
        if(ack =true){
            log.info("交换机已经收到id为:{}的消息",id);
        }else {
            log.info("交换机还未收到id为:{}的消息，由于原因{}",id,reason);
        }
    }

    //队列发生故障（回退消息）
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("消息:{}被服务器退回，退回原因:{}, 交换机是:{}, 路由 key:{}",new String(returned.getMessage().getBody()),returned.getReplyText(),
                returned.getExchange(),returned.getRoutingKey());

    }
}
