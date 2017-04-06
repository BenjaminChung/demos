package com.whale.demo.mq.rabbitmq.spring;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2015/8/5.
 */
public class Producer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-rabbitmq-producers.xml");
        AmqpTemplate amqpTemplate = classPathXmlApplicationContext.getBean(AmqpTemplate.class);
        amqpTemplate.convertAndSend("ld_moistureoiltest_queue5","{userId:168283}");
        System.out.println("send something");
    }
}
