package com.youjifu.springboot_rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author YouJiFu
 * @version 1.0
 * @Date 2022/4/22 15:35
 */
@Slf4j
@Component
public class confirmConsumer {
    @RabbitListener(queues = "confirm.queue")
    public void receiveConfirm(Message message){
        String msg = new String(message.getBody());
        log.info("接收到队列confirm.queue的消息为:{}" ,msg);
    }
}
