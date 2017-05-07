package com.whale.demo.dao;

/**
 * Created by benjaminchung on 17/5/7.
 */
public class DBQuery implements IDBQuery{

    public DBQuery(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public String request() {
        return "request string";
    }
}