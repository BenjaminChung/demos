package com.whale.demo.mq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2015/7/31.
 */
public class Producer {
    //队列名称
    private final static String QUEUE_NAME = "open_api_queue_one";
    //生成数据到mq
    public void sendMsg(String msg) {
        /**
         * 创建连接连接到MabbitMQ
         */
        try {
            ConnectionFactory factory = new ConnectionFactory();
            //设置MabbitMQ所在主机ip或者主机名，单机这样操
            factory.setUsername("esnmq");
            factory.setPassword("wE2Ew0wwGS");
            factory.setVirtualHost("/esnhost");
            //创建一个连接
            //连接集群 这样操作
            Connection connection = factory.newConnection(new Address[]{new Address("172.20.1.177",5672)});
            //创建一个频道
            Channel channel = connection.createChannel();
            //指定一个队列
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //往队列中发出一条消息,第三个参数为持久化
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            System.out.println(" [x] Sent '" + msg + "'");
            //关闭频道和连接
//            channel.close();
//            connection.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.sendMsg("hello sz");
    }
}
