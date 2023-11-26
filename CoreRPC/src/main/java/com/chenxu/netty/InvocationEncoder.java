package com.chenxu.netty;

import com.chenxu.common.Invocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.lang3.SerializationUtils;

import java.security.MessageDigest;

public class InvocationEncoder extends MessageToByteEncoder<Invocation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Invocation invocation, ByteBuf out) throws Exception {
        // 将对象序列化为字节数组
        byte[] data = SerializationUtils.serialize(invocation);

        // 写入字节数组到 ByteBuf
        out.writeBytes(data);
    }
}
