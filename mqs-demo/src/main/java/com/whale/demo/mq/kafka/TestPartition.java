package com.whale.demo.mq.kafka;
import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

import java.util.Random;

/**
 * Created by Benjamin(zhongjianbin) on 2015/8/13 9:20.
 */
public class TestPartition implements Partitioner{

    public TestPartition (VerifiableProperties props) {

    }

    @Override
    public int partition(Object key, int numPartitions) {
        if (key == null) {
            Random random = new Random();
            System.out.println("key is null ");
            return random.nextInt(numPartitions);
            } else {
                int result = Math.abs(key.hashCode())%numPartitions;
                System.out.println("key is "+ key+ " partitions is "+ result);
            return result;
            }
    }
}
