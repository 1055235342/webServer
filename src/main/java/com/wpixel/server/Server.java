package com.wpixel.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Server {

	public void run(int port){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	//创建辅助工具类
            ServerBootstrap b = new ServerBootstrap();
            //指定两个线程组
            b.group(bossGroup, workerGroup);
            //指定NIO模式
            b.channel(NioServerSocketChannel.class);
            //设置tcp链接缓冲区
            b.option(ChannelOption.SO_BACKLOG, 1024);
            //设置发送缓冲大小
            b.option(ChannelOption.SO_SNDBUF, 1024);
            //设置接受缓冲大小
            b.option(ChannelOption.SO_RCVBUF, 1024);
            //保持连接
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
    			@Override
    			protected void initChannel(SocketChannel sc) throws Exception {
    				//sc.pipeline().addLast(new ReadTimeoutHandler(8)); //设置时间：如果30秒内没有收到客户端发送的数据，则认为链接断开
    				sc.pipeline().addLast(new HttpResponseEncoder());
    				sc.pipeline().addLast(new HttpRequestDecoder());
    				sc.pipeline().addLast(new HttpObjectAggregator(65536));
    				sc.pipeline().addLast(new ChunkedWriteHandler());
    				sc.pipeline().addLast(new HttpServerHandler());
    			}
    		});

			System.out.println("启动成功... ...");
            ChannelFuture cf = b.bind(port).sync();
            cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
