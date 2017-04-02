package com.whale.demo.netty.httpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Created by benjaminchung on 2017/4/2.
 */
public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {

    private ByteBufToBytes reader;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            System.out.println("messageType:" + request.headers().get("messageType"));
            System.out.println("businessType:" + request.headers().get("businessType"));
            if (HttpHeaders.isContentLengthSet(request)) {
                reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(request));
            }
        }

        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            reader.reading(content);
            content.release();

            if (reader.isEnd()) {
                String resultStr = new String(reader.readFull());
                System.out.println("Client said:" + resultStr);

                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer("I am ok"
                        .getBytes()));
                response.headers().set(CONTENT_TYPE, "text/plain");
                response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
                response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                ctx.write(response);
                ctx.flush();
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HttpServerInboundHandler.channelReadComplete");
        ctx.flush();
    }
}
