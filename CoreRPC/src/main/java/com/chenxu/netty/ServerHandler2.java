package com.chenxu.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler2 extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        // 读取客户端发送的数据
        byte[] data = new byte[msg.readableBytes()];
        msg.readBytes(data);
        String message = new String(data, "UTF-8");
        System.out.println("Received message from client: " + message);

        // 向客户端发送回复
        String reply = "Hello, Netty!";
        ByteBuf replyBuffer = ctx.alloc().buffer(reply.length());
        replyBuffer.writeBytes(reply.getBytes("UTF-8"));
        ctx.writeAndFlush(replyBuffer);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 异常处理
        cause.printStackTrace();
        ctx.close();
    }
}
