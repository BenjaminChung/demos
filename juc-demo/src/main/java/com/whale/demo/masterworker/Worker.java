package com.whale.demo.masterworker;

import java.util.Map;
import java.util.Queue;

/**
 * Created by benjaminchung on 16/8/31.
 */
public class Worker implements Runnable{
    //任务队列 用于取得子任务
    protected Queue<Object> workQueue;
    //任务处理子集
    protected Map<String,Object> resultMap;

    public void setWorkQueue(Queue<Object> workQueue){
        this.workQueue = workQueue;
    }

    public void setResultMap(Map<String,Object> resultMap){
        this.resultMap = resultMap;
    }

    public Object handle(Object input){
        return input;
    }

    public void run() {

        while(true){
            Object input = workQueue.poll();
            if(input == null){
                break;
            }
            Object re = handle(input);
            resultMap.put(Integer.toString(input.hashCode()),re);
        }

    }
}