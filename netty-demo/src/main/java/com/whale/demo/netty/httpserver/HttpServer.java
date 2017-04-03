package com.whale.demo.netty.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * Created by benjaminchung on 2017/4/2.
 */
public class HttpServer {

    public void start(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
//                            //1.server端发送对内容编码
//                            ch.pipeline().addLast(new HttpResponseEncoder());
//                            //2.server端接收内容，对内容解码
//                            ch.pipeline().addLast(new HttpRequestDecoder());
//                            //3.注册handler
//                            ch.pipeline().addLast(new HttpServerInboundHandler());
                            // 都属于ChannelOutboundHandler，逆序执行
                            ch.pipeline().addLast(new HttpResponseEncoder());
                            ch.pipeline().addLast(new StringEncoder());
                            //都属于ChannelIntboundHandler，按照顺序执行
                            ch.pipeline().addLast(new HttpRequestDecoder());
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new BusinessHandler());


                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)//最多允许128个连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("server started");
            channelFuture.channel().closeFuture().sync();
        }finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        HttpServer httpServer = new HttpServer();
        httpServer.start(1234);
    }
}
