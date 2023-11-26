package com.chenxu.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer2 {
    public static void main(String[] args) throws Exception {
        // 创建 Boss 线程组和 Worker 线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建 ServerBootstrap 对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 在这里可以添加自定义的 ChannelHandler
                            pipeline.addLast(new ServerHandler2());
                        }
                    });

            // 绑定端口并启动服务器
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            System.out.println("Server started on port 8080");

            // 等待服务器关闭
            future.channel().closeFuture().sync();
        } finally {
            // 优雅地关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
