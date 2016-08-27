package com.whale.demo.datasource;

/**
 * 数据源持有者
 * Created by Benjamin Chung
 */
public class DataSourceContextHolder {

    public final static String DATA_SOURCE_MASTER = "DS_MASTER";
    public final static String DATA_SOURCE_SLAVE = "DS_SLAVE";
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static String getDatasouce() {
        return contextHolder.get();
    }

    public static void setDataSource(String dataSourceName) {
        contextHolder.set(dataSourceName);
    }
}
