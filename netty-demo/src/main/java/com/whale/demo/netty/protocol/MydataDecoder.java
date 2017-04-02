package com.whale.demo.netty.protocol;

import com.whale.demo.netty.httpserver.ByteBufToBytes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by benjaminchung on 2017/4/2.
 */
public class MydataDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBufToBytes read = new ByteBufToBytes();
        Object obj = ByteObjConverter.ByteToObject(read.read(in));
        out.add(obj);
    }
}
