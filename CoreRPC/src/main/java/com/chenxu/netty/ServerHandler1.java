package com.chenxu.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.logging.Logger;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName ServerHandler
 * @Description 服务回调
 * @Date 22:15 2022/6/28
 **/
public class ServerHandler1 extends ChannelInboundHandlerAdapter {
    public static final Logger logger = Logger.getLogger("ServerHandler");
    public ServerHandler1() {
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
        logger.info("======服务端通道激活======");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 当通道有数据读取时的监听
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            // 传输buffer 对象
            ByteBuf buf = (ByteBuf) msg;
            // 定义byte数组
            byte[] req =new byte[buf.readableBytes()];
            // 从缓冲区获取数据到req
            buf.readBytes(req);
            // 读取数据转为字符串
            String body = new String(req,"utf-8");
            logger.info("服务端读取的数据：" + body);
            // 响应给客户端的数据
            ctx.writeAndFlush(Unpooled.copiedBuffer("netty server response data: you are beautiful".getBytes()))
            // 添加addListener可以触发关闭通道监听事件，客户端短链接场景使用
            // .addListener(ChannelFutureListener.CLOSE);
            ;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 当我们读取完成数据时触发的操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("====== 服务端数据读取完成 ======");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    /**
     * 当我们读取数据异常的时候触发的监听
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("====== 服务端数据读取异常 ======");
        logger.info(cause.getMessage());
        ctx.close();
    }
}
