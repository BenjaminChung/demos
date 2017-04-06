package com.whale.demo.mq.kafka;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/7/25.
 */
public class Main {
    public static void main(String[] args) {
        String topic = "mlx_log_queue";

        LogConsumer consumer = new LogConsumer(topic);
        consumer.start();

//        LogProducer producer = new LogProducer(topic);
//        producer.start();


    }
}

class LogProducer extends Thread {
    private final kafka.javaapi.producer.Producer<String, String> producer;
    private final String topic;
    private final Properties props = new Properties();

    public LogProducer(String topic) {
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //props.put("metadata.broker.list", "120.132.84.17:9092");
        props.put("metadata.broker.list", "123.59.74.211:9092,123.59.74.212:9092");
        props.put("request.required.acks", "1");
        props.put("num.partitions",5);
        props.put("partitioner.class","com.ld.test.kafka.TestPartition");
        producer = new kafka.javaapi.producer.Producer<String, String>(new ProducerConfig(props));
        this.topic = topic;
    }

    public void send() {
        Random random = new Random();
        producer.send(new KeyedMessage<String, String>(topic, random.nextInt(4)+"","this is a test from 2222"));
    }

    public void run() {
        int messageNo = 1;
        while (messageNo < 10) {
            Random random = new Random();
            String messageStr = new String("Message_" + messageNo);
            producer.send(new KeyedMessage<String, String>(topic,random.nextInt(4)+"", messageStr));
            messageNo++;
        }
    }

}

class LogConsumer extends Thread {
    private final ConsumerConnector consumer;
    private final String topic;

    public LogConsumer(String topic) {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig());
        this.topic = topic;
    }

    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        //props.put("zookeeper.connect", "120.132.84.17:2181");
        props.put("zookeeper.connect", "123.59.74.211:2181,123.59.74.212:2181");
        props.put("group.id", "ld-log-consumer-group");
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);

    }

    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for(final KafkaStream<byte[], byte[]> stream: consumerMap.get(topic)) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    ConsumerIterator<byte[], byte[]> it = stream.iterator();
                    while (it.hasNext())
                        System.out.println(new String(it.next().message()));
                }
            });

        }
    }

}
