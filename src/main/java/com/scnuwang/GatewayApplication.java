package com.scnuwang;

import com.scnuwang.impl.DefaultHttpProxyServer;
import io.netty.handler.codec.http.HttpRequest;

import java.net.InetSocketAddress;

/**
 *  应用启动类
 */
public class GatewayApplication {
    public static void main(String[] args) {
        DefaultHttpProxyServer.bootstrap()
                .withAddress(new InetSocketAddress("127.0.0.1",9110))
        .withAllowRequestToOriginServer(true)
        .withAllowLocalOnly(false)
        .withConnectTimeout(500)
        .plusActivityTracker(new ActivityTrackerAdapter())
        .withTransportProtocol(TransportProtocol.TCP)
        .start();
    }
}
