package com.ds.test;



import com.alibaba.fastjson.JSON;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Benjamin
 * @version V1.0
 * @Description: TODO(描述)
 * @date 2015/4/9 12:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@WebAppConfiguration
public class SpringTestBase {
    @Autowired
    private WebApplicationContext wac;


    protected <T> T toBean(Object object, Class<T> beanClass)
    {
        return (T) JSON.parseObject(object.toString(),beanClass);
    }
}
