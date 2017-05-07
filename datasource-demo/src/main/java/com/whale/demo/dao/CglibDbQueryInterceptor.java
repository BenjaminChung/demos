package com.whale.demo.dao;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by benjaminchung on 17/5/7.
 */
public class CglibDbQueryInterceptor implements MethodInterceptor{
    IDBQuery real = null;

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(real == null)
            real = new DBQuery();
        return real.request();
    }

    public static IDBQuery createCglibProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setCallback(new CglibDbQueryInterceptor());
        enhancer.setInterfaces(new Class[]{IDBQuery.class});
        IDBQuery cglibProxy = (IDBQuery)enhancer.create();
        return cglibProxy;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        IDBQuery query = createCglibProxy();
        System.out.println("create cglib proxy:"+(System.currentTimeMillis()-startTime)+"ms");
        startTime = System.currentTimeMillis();
        System.out.println(query.request());
        System.out.println("call method cost time:"+(System.currentTimeMillis() - startTime));
    }
}
