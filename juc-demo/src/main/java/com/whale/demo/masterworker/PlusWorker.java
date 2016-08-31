package com.whale.demo.masterworker;

/**
 * Created by benjaminchung on 16/8/31.
 */
public class PlusWorker extends Worker {

    public Object handle(Object input) {
        Integer i = (Integer) input;
        return i * i;
    }


}
