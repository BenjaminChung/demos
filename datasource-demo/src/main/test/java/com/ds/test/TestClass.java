package com.ds.test;

import org.apache.commons.dbutils.QueryRunner;

import java.util.UUID;


/**
 * Created by Benjamin(zhongjianbin) on 2015/9/11 15:15.
 */
public class TestClass {

    @org.junit.Test
    public void test(){
//        System.out.println(ThreadLocalRandom.current().nextInt(20));
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }
}
