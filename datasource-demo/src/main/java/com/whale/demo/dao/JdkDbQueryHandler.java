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
        long startTime = System.currentTimeMillis();
        IDBQuery query = createJdkProxy();
        System.out.println("create jdk proxy:"+(System.currentTimeMillis()-startTime)+"ms");
        startTime = System.currentTimeMillis();
        System.out.println(query.request());
        System.out.println("call method cost time:"+(System.currentTimeMillis() - startTime));
    }
}





