package com.whale.demo.datasource;

import com.ld.datasource.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

/**
 * 数据源选择切面类
 * Created by Benjamin.Chung
 */
@Component
@Aspect
public class DatasourceChooseAspect {

    private static final String SELECT = "select";

    private static final String INSERT = "insert";

    private static final String DELETE = "delete";

    private static final String UPDATE = "update";

    @Before(value="execution(* com.ld.datasource.DAO.*.*(..))")
    public void beforeExecute(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        Class classz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature())
                .getMethod().getParameterTypes();
        String methodName = joinPoint.getSignature().getName();
        try {
            Method method = classz.getMethod(methodName, parameterTypes);
            //如果方法有注解则执行注解指定的库
            if (method != null && method.isAnnotationPresent(DataSource.class)) {
                DataSource data = method.getAnnotation(DataSource.class);
                DataSourceContextHolder.setDataSource(data.value());
            }else {
                //默认方式
                //读操作走从库
                if (methodName.startsWith(SELECT)) {
                    DataSourceContextHolder.setDataSource(DataSourceContextHolder.DATA_SOURCE_SLAVE);
                }//写操作走主库
                else if ((methodName.startsWith(INSERT) ||
                        methodName.startsWith(UPDATE) ||
                        methodName.startsWith(DELETE))) {
                    DataSourceContextHolder.setDataSource(DataSourceContextHolder.DATA_SOURCE_MASTER);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
