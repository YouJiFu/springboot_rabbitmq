package com.youjifu.springboot_rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/21 14:36
 * 队列消费者,接收消息
 */
@Slf4j
@Component //创建Bean实例
public class DadeLetterQueueConsumer {

    //接收消息
    @RabbitListener(queues ="QD" )
    public void receiveQD (Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody(), "UTF-8");
        log.info("当前时间{},收到的消息为:{}",new Date().toString(),msg);
    }
}
