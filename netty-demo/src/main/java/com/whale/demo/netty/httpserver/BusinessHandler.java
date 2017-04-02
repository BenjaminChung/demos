package com.whale.demo.netty.httpserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by benjaminchung on 2017/4/2.
 */
public class BusinessHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String clientMsg = "client said : " + (String) msg;
        System.out.println("BusinessHandler read msg from client :" + clientMsg);
        ctx.write("I am very OK!");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
