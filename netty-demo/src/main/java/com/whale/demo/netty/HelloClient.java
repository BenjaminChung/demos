package com.whale.demo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 * Created by benjaminchung on 2017/4/2.
 */
public class HelloClient {
    /**
     * 连接服务器
     * @param host
     * @param port
     * @throws InterruptedException
     */
    public void connect(String host, int port) throws InterruptedException {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HelloClientInHandler());
                }
            });

            // Start the client.
            ChannelFuture future = bootstrap.connect(host, port).sync();
            // Wait until the connection is closed.
            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        HelloClient helloClient = new HelloClient();
        try {
            helloClient.connect("127.0.0.1",1234);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
