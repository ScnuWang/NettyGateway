package com.scnuwang.handler;

import com.scnuwang.Initializer.ClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;

import java.net.InetSocketAddress;

/*
    如果是Http请求，新建一个客户端，转发到下游服务器
 */
public class ProxyToServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            Bootstrap b = new Bootstrap();
            // 直接复用客户端连接池
            b.group(ctx.channel().eventLoop())
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 9112))
                    .handler(new ClientInitializer(ctx.channel()));
            ChannelFuture channelFuture = b.connect();
            channelFuture.addListener((ChannelFutureListener)f -> {
                if (f.isSuccess()){
                    // 发送请求到服务端
                    f.channel().writeAndFlush(msg);
                }else {
                    f.channel().close();
                }
            });
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
