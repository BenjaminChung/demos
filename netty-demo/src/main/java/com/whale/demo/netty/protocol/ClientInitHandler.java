package com.whale.demo.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by benjaminchung on 2017/4/2.
 */
public class ClientInitHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientInitHandler.channelActive");
        Mydata mydata = new Mydata();
        mydata.setId("xxxxx");
        mydata.setMsg("msgxxxx");
        ctx.write(mydata);
        ctx.flush();
    }
}
