package com.whale.demo.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by benjaminchung on 2017/4/2.
 */
public class MydataEncoder extends MessageToByteEncoder<Mydata> {
    protected void encode(ChannelHandlerContext ctx, Mydata msg, ByteBuf out) throws Exception {
        byte[] datas = ByteObjConverter.ObjectToByte(msg);
        out.writeBytes(datas);
        ctx.flush();
    }
}
