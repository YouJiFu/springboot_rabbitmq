package com.youjifu.springboot_rabbitmq.controller;

import com.youjifu.springboot_rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/22 15:22
 * 生产者，发送消息(高级)
 */
@Slf4j
@RestController
@RequestMapping("/confirm")
public class ProductController {
    //发消息
    @Autowired
    RabbitTemplate rabbitTemplate;
    @GetMapping("sendMessage/{message}")
    public void sendMsg(@PathVariable String message){
        CorrelationData correlationData1 = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.EXCHANGE_NAME,ConfirmConfig.ROUTING_KEY, message+"key1",correlationData1);
        log.info("发送消息内容为:{}",message+"key1");

        CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(ConfirmConfig.EXCHANGE_NAME,ConfirmConfig.ROUTING_KEY+"2", message+"key12",correlationData2);
        log.info("发送消息内容为:{}",message+"key12");

    }
}
