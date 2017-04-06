package com.whale.demo.mq.rabbitmq.spring;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.Date;


/**
 * Created by Administrator on 2015/8/5.
 */
public class ConsumerListener implements MessageListener{

    Logger logger = Logger.getLogger(ConsumerListener.class);
    @Override
    public void onMessage(Message message) {
        String receiveMsg=null;
        try {
            receiveMsg =new String(message.getBody(),"utf-8");
        } catch (Exception e1) {
            e1.printStackTrace();
            return ;
        }
        System.out.println("receiveMsg:"+receiveMsg);
        if (null == receiveMsg || "".equals(receiveMsg)) {
            logger.error("ConsumerTask   receiveMsg is null  Time is " + new Date());
            return ;
        } else {
            logger.info("ConsumerTask   receiveMsg " + receiveMsg);
        }
    }
}
