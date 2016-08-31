package com.whale.demo.masterworker;

import java.util.Map;
import java.util.Set;

/**
 * Created by benjaminchung on 16/8/31.
 */
public class Main {

    public static void main(String[] args) {
        Master master = new Master(new PlusWorker(),5);
        for(int i=0;i<100;i++){
            master.submit(i);
        }
        master.execute();
        int re = 0;
        Map<String,Object> resultMap = master.getResultMap();
        while(resultMap.size()>0||!master.isComplete()) {
            Set<String> keys = resultMap.keySet();
            String key = null;
            for (String k : keys) {
                 key = k;
                 break;
            }
            Integer i = null;
            if(key!=null){
                i = (Integer)resultMap.get(key);
            }
            if(i!=null){
                re += i;
            }
            if(key!=null){
                resultMap.remove(key);
            }
        }
        System.out.println(re);
    }
}
