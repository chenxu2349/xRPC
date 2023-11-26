package com.chenxu.netty;

import com.chenxu.common.Invocation;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.apache.commons.lang3.SerializationUtils;

import java.util.List;

public class InvocationDecoder extends ByteToMessageCodec<Invocation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Invocation invocation, ByteBuf out) throws Exception {

        System.out.println("To encode invocation: " + invocation);
        // 将对象序列化为字节数组
        byte[] data = SerializationUtils.serialize(invocation);
        System.out.println("---1:" + data.length);

        // 写入字节数组到 ByteBuf
        out.writeInt(data.length); // 写长度这步非常重要！！！
        out.writeBytes(data);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 检查是否有足够的字节可供读取
        if (in.readableBytes() < 4) {
            // 如果没有足够的字节，等待更多的数据
            System.out.println("bytes not enough, wait...");
            return;
        }

        // 读取字节长度并检查是否有足够的字节可供读取
        int dataLength = in.readInt();
        System.out.println("---3:"+dataLength);
        if (in.readableBytes() < dataLength) {
            // 如果没有足够的字节，等待更多的数据
            in.resetReaderIndex();
            return;
        }

        // 读取字节数组
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        // 将字节数组反序列化为对象
        Invocation invocation = SerializationUtils.deserialize(data);

        // 将对象添加到输出列表
        out.add(invocation);
    }
}
