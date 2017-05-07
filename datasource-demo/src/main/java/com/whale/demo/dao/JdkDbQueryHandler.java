package com.whale.demo.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by benjaminchung on 17/5/7.
 */
public class JdkDbQueryHandler implements InvocationHandler{
    IDBQuery real = null;
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(real == null)
            real = new DBQuery();
        return real.request();
    }

    public static IDBQuery createJdkProxy(){
        IDBQuery jdkProxy = (IDBQuery) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{IDBQuery.class},
                new JdkDbQueryHandler()
        );
        return jdkProxy;
    }

    public static void main(String[] args) {
        IDBQuery query = null;
        long startTime = System.currentTimeMillis();
        query = createJdkProxy();
        System.out.println("create jdk proxy:"+(System.currentTimeMillis()-startTime)+"ms");
        System.out.println(query.request());
    }
}


interface IDBQuery {
    String request();
}

class DBQuery implements IDBQuery{

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
