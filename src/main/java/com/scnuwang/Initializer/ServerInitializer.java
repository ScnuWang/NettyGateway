package com.scnuwang.Initializer;

import com.scnuwang.handler.LoginVerifyHandler;
import com.scnuwang.handler.ProxyToServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/*
    启动初始化
 */
public class ServerInitializer extends ChannelInitializer {
    protected void initChannel(Channel ch){
        // 服务端Http协议解析
        ch.pipeline().addLast("http",new HttpServerCodec());
        // 以请求块的方式写
        ch.pipeline().addLast("stream",new ChunkedWriteHandler());
        // 将Http信息聚合到FullHttpRequest
        ch.pipeline().addLast("httpAggreator",new HttpObjectAggregator(8192));
        // 网关对Http请求拦截处理
        ch.pipeline().addLast("loginVerify",new LoginVerifyHandler());
        // 转发请求到下游服务
        ch.pipeline().addLast("proxy",new ProxyToServerHandler());
    }
}
