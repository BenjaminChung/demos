package com.whale.demo.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by benjaminchung on 2016/10/17.
 */
public class SemaphoreTest {
    //10个流量
    static Semaphore semaphore = new Semaphore(10);
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        for(int i = 0;i<50;i++) {
            executorService.submit(new Runnable() {
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("save data");
                        System.out.println(semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    semaphore.release();
                }
            });
        }
    }
}
