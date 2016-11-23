package com.whale.demo.idgenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Id生成器
 * Created by benjaminchung on 16/9/19.
 */
public class IdGenerator {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public  static Integer getNextNum(){
//        ReentrantLock reentrantLock = new ReentrantLock();
//        try {
//            reentrantLock.lock();
            return atomicInteger.getAndIncrement();
//        }finally {
//            reentrantLock.unlock();
//        }
    }


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for(int index=0;index<10;index++) {
            executorService.submit(new Thread("name:"+index) {
                public void run() {
                    System.out.println(this.getName()+":"+IdGenerator.getNextNum());
                }
            });
        }
    }


}
