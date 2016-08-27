package com.ds.test;

import com.ld.datasource.DAO.UserDAO;
import com.ld.datasource.DataSourceContextHolder;
import com.ld.datasource.bussiness.UserService;
import com.ld.web.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

/**
 * Created by Benjamin(zhongjianbin) on 2015/9/7 10:47.
 */
public class TestDataSource extends SpringTestBase {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserService userService;

    @Autowired
    UserDAO userDAO;

    @Test
    public void testAnnotaionMaster() {
        userDAO.selectUserCount();
    }

    @Test
    public void testCodeSlave() {
        userDAO.selectFromSlave();
    }

    @Test
    public void testChanngeDatasourceToSlave() {
        DataSourceContextHolder.setDataSource(DataSourceContextHolder.DATA_SOURCE_SLAVE);
        String sql = "select count(1) from ld_user";
        //slave user count:430535
        System.out.println("slave user count:" + jdbcTemplate.queryForObject(sql, Integer.class));
    }


    @Test
    public void getId() {
        String insertSql = "REPLACE INTO ld_reply_sequence(stub)values('a')";
        jdbcTemplate.execute(insertSql);
        String sql = "SELECT LAST_INSERT_ID()";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(result);
    }

    @Test
    public void sql() {
        for (int index = 1; index < 101; index++) {
            System.out.println("create table ld_bbs_reply" + index + " select * from ld_bbs_reply where 0;");

        }
    }
}
