package com.ds.test;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by benjaminchung on 2016/10/15.
 */
public class TestDbUtils {

    @Test
    public void testdb() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/mss" ;
        String username = "root" ;
        String password = "123" ;
        Connection con = null;
        try{
             con = DriverManager.getConnection(url , username , password ) ;
        }catch(SQLException se){
            System.out.println("数据库连接失败！");
            se.printStackTrace() ;
        }
        QueryRunner queryRunner = new QueryRunner();
        //List<Meeting> list = queryRunner.query(con,"select * from meeting", new BeanListHandler<Meeting>(Meeting.class));
        Map<String,String> columnMap = new HashMap<String,String>();
        columnMap.put("create_time","createTime");
        List<Meeting> list  =queryRunner.query(con,"select * from meeting", new BeanListHandler<Meeting>(Meeting.class, new BasicRowProcessor(new BeanProcessor(columnMap))));
        for(Meeting meeting:list){
            System.out.println(meeting.getName());
            System.out.println(meeting.getId());
            System.out.println(meeting.getCreateTime());
        }

        PreparedStatement statement = con.prepareStatement("");
        ResultSet resultSet = statement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

    }

    @Test
    public void testDescriptor() throws IntrospectionException {
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(Meeting.class).getPropertyDescriptors();
        for(PropertyDescriptor propertyDescriptor:propertyDescriptors){
            System.out.println(propertyDescriptor.getReadMethod()+":"+propertyDescriptor.getWriteMethod());
            System.out.println(propertyDescriptor.getPropertyType());
        }
    }
}

