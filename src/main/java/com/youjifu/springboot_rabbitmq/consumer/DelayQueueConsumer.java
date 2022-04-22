package com.youjifu.springboot_rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/21 21:26
 */
@Slf4j
@Component
public class DelayQueueConsumer {
    @RabbitListener(queues = "delay.queue")
    public void receiveDelay(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间{},收到延迟队列的消息为:{}",new Date().toString(),msg);
    }
}
