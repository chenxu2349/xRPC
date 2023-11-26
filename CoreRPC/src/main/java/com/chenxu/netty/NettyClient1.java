package com.chenxu.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName NettyClient
 * @Description
 * @Date 22:27 2022/6/28
 **/
public class NettyClient1 {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 添加自定义的handler
                        socketChannel.pipeline().addLast(new ClientHandler1());
                    }
                });
        ChannelFuture channelFuture = b.connect("127.0.0.1", 8888).syncUninterruptibly();
        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("netty 客户端请求数据:zuiyu netty 服务发送数据测试".getBytes()));
        // 释放链接
        channelFuture.channel().closeFuture().sync();
    }
}
