package com.whale.demo.netty.protocol;

import java.io.Serializable;

/**
 * Created by benjaminchung on 2017/4/2.
 */
public class Mydata implements Serializable {
    private String id;
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "id:" + id + ",msg:" + msg;
    }
}
