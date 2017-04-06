package com.whale.demo.mq.rabbitmq.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2015/8/5.
 */
public class Consumer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring-rabbitmq-consumers.xml");
        classPathXmlApplicationContext.start();
    }
}
