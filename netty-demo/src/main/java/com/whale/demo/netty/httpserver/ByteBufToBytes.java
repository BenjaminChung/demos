package com.whale.demo.netty.httpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by benjaminchung on 2017/4/2.
 */
public class ByteBufToBytes {

    private ByteBuf temp;

    private boolean end = true;

    public ByteBufToBytes(){

    }

    public ByteBufToBytes(int length) {
        this.temp = Unpooled.buffer(length);
    }

    public void reading(ByteBuf datas) {
        datas.readBytes(temp, datas.readableBytes());
        if (this.temp.writableBytes() != 0) {
            end = false;
        } else {
            end = true;
        }
    }

    public boolean isEnd() {
        return end;
    }

    public byte[] readFull() {
        if (end) {
            byte[] contentByte = new byte[this.temp.readableBytes()];
            this.temp.readBytes(contentByte);
            this.temp.release();
            return contentByte;
        } else {
            return null;
        }
    }

    public byte[] read(ByteBuf datas) {
        byte[] bytes = new byte[datas.readableBytes()];
        datas.readBytes(bytes);
        return bytes;
    }
}
