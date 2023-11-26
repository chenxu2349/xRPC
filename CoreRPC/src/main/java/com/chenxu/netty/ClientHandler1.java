package com.chenxu.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.logging.Logger;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName ClientHandler
 * @Description
 * @Date 22:32 2022/6/28
 **/
public class ClientHandler1 extends ChannelInboundHandlerAdapter {
    public static final Logger LOGGER = Logger.getLogger("ClientHandler");

    public ClientHandler1() {
        super();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("====== 客户端通道激活======");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // 传输buffer 对象
            ByteBuf buf = (ByteBuf)msg;
            // 定义byte数组
            byte[] req =new byte[buf.readableBytes()];
            // 从缓冲区获取数据到req
            buf.readBytes(req);
            // 读取数据转为字符串
            String body = new String(req,"utf-8");
            LOGGER.info("客户端读取的数据："+body);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("====== 客户端读取数据完毕======");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("====== 客户端读取数据异常======");
        ctx.close();
    }
}
