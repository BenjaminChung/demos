package com.whale.demo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端消息处理器
 * Created by benjaminchung on 2017/4/2.
 */
public class HelloClientInHandler extends ChannelInboundHandlerAdapter {

    // 接收server端的消息，并打印出来
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client channel read");
        ByteBuf source = (ByteBuf) msg;
        byte[] result1 = new byte[source.readableBytes()];
        source.readBytes(result1);
        System.out.println("Server said:" + new String(result1));
        source.release();
    }

    //连接成功后，向server发送消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channel active");
        String msg = "I am comming";
        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());
        encoded.writeBytes(msg.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }
}
