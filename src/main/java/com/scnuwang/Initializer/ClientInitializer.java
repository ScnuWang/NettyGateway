package com.scnuwang.Initializer;

import com.scnuwang.handler.ServerToProxyHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

/*
    客户端初始化处理器
 */
public class ClientInitializer extends ChannelInitializer {
    private Channel channel;

    public ClientInitializer(Channel channel){
        this.channel = channel;
    }


    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new HttpClientCodec());
        ch.pipeline().addLast(new HttpObjectAggregator(8192));
        ch.pipeline().addLast(new ServerToProxyHandler(channel));
    }
}
