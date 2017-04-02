package com.whale.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端请求处理器
 * Created by benjaminchung on 2017/4/2.
 */
public class HelloServerInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf source = (ByteBuf) msg;
        byte[] bytes = new byte[source.readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        source.readBytes(bytes);
        String resultStr = new String(bytes);
        System.out.println("client said:" + resultStr);
        //释放资源，重要
        source.release();
        String respones = "Roger that";
        //编码数据
        //1.
        ByteBuf encodeData = ctx.alloc().buffer(4 * respones.length());
        encodeData.writeBytes(respones.getBytes());
        //2.
        ctx.write(encodeData);
        //3.
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
