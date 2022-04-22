package com.youjifu.springboot_rabbitmq.consumer;

import com.youjifu.springboot_rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/22 17:35
 */
@Component
@Slf4j
public class WarningConsumer {
    //接收报警信息
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message){
        String msg = new String(message.getBody());
        log.info("报警发现不可路由消息：{}",msg);
    }
}
