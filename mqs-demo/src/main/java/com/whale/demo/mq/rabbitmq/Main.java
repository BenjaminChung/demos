package com.whale.demo.mq.rabbitmq;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2015/7/30.
 */
public class Main {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        String QUENE_NAME = "ld-rabbitmq-demo";
        RbConsumer consumer = new RbConsumer(QUENE_NAME);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(consumer);
        //executorService.shutdown();

        RbProducer producer = new RbProducer(QUENE_NAME);

        for (int i = 0; i < 10; i++) {
            HashMap message = new HashMap();
            message.put("message number", i);
            producer.sendMessage(message);
            System.out.println("Message Number " + i + " sent.");
        }
    }
}


abstract class EndPoint {
    protected Channel channel;
    protected Connection connection;
    protected String endPointName;

    public EndPoint(String endpointName) throws IOException, TimeoutException {
        this.endPointName = endpointName;

        //Create a connection factory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("ld");
        factory.setPassword("ldadmin");
        //getting a connection
        connection = factory.newConnection(new Address[]{new Address("123.59.74.211", 5672), new Address("123.59.74.212", 5672)});

        //creating a channel
        channel = connection.createChannel();

        //declaring a queue for this channel. If queue does not exist,
        //it will be created on the server.
        channel.queueDeclare(endpointName, true, false, false, null);
    }


    /**
     * 关闭channel和connection。并非必须，因为隐含是自动调用的。
     *
     * @throws IOException
     */
    public void close() throws IOException, TimeoutException {
        this.channel.close();
        this.connection.close();
    }
}

class RbProducer extends EndPoint {

    public RbProducer(String endpointName) throws IOException, TimeoutException {
        super(endpointName);
    }

    public void sendMessage(Serializable object) throws IOException {
        channel.basicQos(1);
        channel.basicPublish("", endPointName, null,JSON.toJSONString(object).getBytes());
    }
}


class RbConsumer extends EndPoint implements Runnable, com.rabbitmq.client.Consumer {

    public RbConsumer(String endpointName) throws IOException, TimeoutException {
        super(endpointName);
    }

    /**
     * Called when consumer is registered.
     *
     * @param consumerTag
     */
    @Override
    public void handleConsumeOk(String consumerTag) {
        System.out.println("Consumer " + consumerTag + " registered");
    }

    @Override
    public void handleCancelOk(String s) {

    }

    @Override
    public void handleCancel(String s) throws IOException {

    }

    @Override
    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    @Override
    public void handleRecoverOk(String s) {

    }


    /**
     * Called when new message is available.
     *
     * @param consumerTag
     * @param envelope
     * @param basicProperties
     * @param bytes
     * @throws IOException
     */
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        Map map = JSON.parseObject(new String(bytes), Map.class);
        System.out.println("Message Number " + map.get("message number") + " received.");
    }

    @Override
    public void run() {
        try {
            channel.basicConsume(endPointName, true, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
