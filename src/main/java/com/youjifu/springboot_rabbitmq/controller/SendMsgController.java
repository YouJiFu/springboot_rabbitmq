package com.youjifu.springboot_rabbitmq.controller;

import com.youjifu.springboot_rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/21 14:17
 * 生产者,发送延迟消息
 */
@Slf4j
@RestController
@RequestMapping("/ttl") //请求映射
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    //开始发送消息
    @GetMapping("/sendMsg/{message}") //获取映射
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间:{},发送一条信息给两个TTL队列：{}", new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10ms的队列" + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40ms的队列" + message);

    }

    //开始发送消息,基于ttl过期时间
    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttlTime) {
        log.info("当前时间：{},发送一条时长{}毫秒 TTL 信息给队列 C:{}", new Date(), ttlTime, message);
        rabbitTemplate.convertAndSend("X", "XC", message, msg -> {
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    //开始发送消息,基于插件的延迟消息
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendDelayedMsg(@PathVariable String message, @PathVariable Integer delayTime) {
        log.info("当前时间：{},发送一条时长{}毫秒 延迟信息给队列 delayQueue:{}", new Date(), delayTime, message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAY_EXCHANGE_NAME,
                DelayedQueueConfig.DELAY_ROUTING_KEY, message, msg -> {
                    msg.getMessageProperties().setDelay(delayTime);
                    return msg;
        });
    }

}
