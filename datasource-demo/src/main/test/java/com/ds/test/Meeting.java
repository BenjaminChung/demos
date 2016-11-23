package com.ds.test;

import java.util.Date;

/**
 * Created by benjaminchung on 2016/10/16.
 */
public class Meeting {
    private Long id;
    private String name;
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
