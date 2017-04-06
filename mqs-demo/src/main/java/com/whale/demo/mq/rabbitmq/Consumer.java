package com.whale.demo.mq.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2015/7/31.
 */
public class Consumer extends Thread{
    //队列名称
    private final static String QUEUE_NAME = "open_api_queue_one";
    private void recv()
    {
        //打开连接和创建频道，与发送端一样
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("esnmq");
        factory.setPassword("wE2Ew0wwGS");
        factory.setVirtualHost("/esnhost");
        Connection connection = null;
        try {
            connection = factory.newConnection(new Address[]{new Address("172.20.1.177",5672)});
            Channel channel = connection.createChannel();
            //声明队列，主要为了防止消息接收者先运行此程序，队列还不存在时创建队列。
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            //每次处理一条消息
            channel.basicQos(1);
            //创建队列消费者
            QueueingConsumer consumer = new QueueingConsumer(channel);
            //指定消费队列
            channel.basicConsume(QUEUE_NAME, true, consumer);
            while (true)
            {
                //nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody());
                System.out.println(" [x] Received '" + message + "'");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        recv();
    }
    public  void recvMsg(){
        start();
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.recvMsg();
    }
}
