package com.scnuwang.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;

/*

 */
public class ServerToProxyHandler extends ChannelInboundHandlerAdapter {
    private Channel clientChannel;

    public ServerToProxyHandler(Channel channel){
        this.clientChannel = channel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        FullHttpResponse response = (FullHttpResponse) msg;
        //修改http响应体返回至客户端
        response.headers().add("hello","world");
        clientChannel.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
