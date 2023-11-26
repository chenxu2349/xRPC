package com.chenxu.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient2 {
    public static void main(String[] args) throws Exception {
        // 创建 EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建 Bootstrap 对象
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 在这里可以添加自定义的 ChannelHandler
                            pipeline.addLast(new ClientHandler2());
                        }
                    });

            // 连接到服务器
            ChannelFuture future = bootstrap.connect("localhost", 8080).syncUninterruptibly();

            // 发送消息给服务器
            String message = "Hello, Server!";
            future.channel().writeAndFlush(Unpooled.copiedBuffer(message.getBytes("UTF-8")));

            // 等待连接关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅地关闭线程组
            group.shutdownGracefully();
        }
    }
}

