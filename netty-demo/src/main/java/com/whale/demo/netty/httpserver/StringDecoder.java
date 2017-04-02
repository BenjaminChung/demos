package com.whale.demo.netty.httpserver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

/**
 * 字符串解码
 * Created by benjaminchung on 2017/4/2.
 */
public class StringDecoder extends ChannelInboundHandlerAdapter {

    private ByteBufToBytes  reader;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("StringDecoder : msg's type is " + msg.getClass());
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(request));
        }

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            reader.reading(content.content());

            if (reader.isEnd()) {
                byte[] clientMsg = reader.readFull();
                System.out.println("StringDecoder : change httpcontent to string ");
                ctx.fireChannelRead(new String(clientMsg));
            }
        }
    }
}
