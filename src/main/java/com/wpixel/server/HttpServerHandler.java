package com.wpixel.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders.Names;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private static Logger logger = Logger.getLogger(HttpServerHandler.class);  

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		String uri = request.getUri();
		HttpMethod method = request.getMethod();
		String path = System.getProperty("user.dir")+"/src/main/resources/com/wpixel/webapp"+uri;
		
		System.out.println("====="+uri+"=="+method);
		
		ByteBuf buf = Unpooled.directBuffer();
		File file = new File(path);
		if(!file.exists()){
			buf.writeBytes("404".getBytes());
		}else{
			InputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[(int)(file.length())];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			byte[] array = out.toByteArray();
			buf.writeBytes(array);
		}
		
		DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
		response.headers().set(Names.CONTENT_TYPE, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8;charset=UTF-8");
		response.headers().set(Names.CONTENT_LENGTH, response.content().readableBytes());
		response.headers().set(Names.CONNECTION, Values.KEEP_ALIVE);
//		response.headers().set(Names.ACCEPT_CHARSET, );
		ctx.writeAndFlush(response);
	}

}
