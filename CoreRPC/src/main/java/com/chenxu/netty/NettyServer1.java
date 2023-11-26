package com.chenxu.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author @醉鱼
 * @link https://github.com/TianPuJun
 * @ClassName NettyService
 * @Description
 * @Date 22:05 2022/6/28
 **/
public class NettyServer1 {
    public static void main(String[] args) throws InterruptedException {
        // 用于接收客户端链接的线程工作组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        // 用于接收客户端链接读写操作的线程组
        EventLoopGroup workGroup = new NioEventLoopGroup();
        // 辅助类，帮助创建netty服务
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                // 绑定两个工作组
                .group(eventLoopGroup,workGroup)
                // 设置nio 模式
                .channel(NioServerSocketChannel.class)
                // option 针对服务器端配置，childOption 针对客户端链接通道配置
                // 设置tcp 缓冲区
                .option(ChannelOption.SO_BACKLOG,1024)
                // 设置发送数据的缓存大小
                .childOption(ChannelOption.SO_SNDBUF,32*1024)
                // 设置读取数据的缓存大小
                .childOption(ChannelOption.SO_RCVBUF,32*1024)
                // 设置保持长链接
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                // 初始化绑定服务通道
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 为通道进行初始化，数据传输过来的适合会进行拦截和执行(可以有多个拦截器)
                        // 这里添加自定义的Handler
                        socketChannel.pipeline().addLast(new ServerHandler1());
                    }
                });

        // 绑定端口并启动服务器
        int port = 8888;
        ChannelFuture sync = serverBootstrap.bind(port).sync();
        System.out.println("Server has started on " + port + " ...");
        // 释放链接
        sync.channel().closeFuture().sync();
        workGroup.shutdownGracefully();
        eventLoopGroup.shutdownGracefully();
    }
}

