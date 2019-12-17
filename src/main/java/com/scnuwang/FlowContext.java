package com.scnuwang;



import com.scnuwang.impl.ClientToProxyConnection;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import java.net.InetSocketAddress;

/**
 * <p>
 * Encapsulates contextual information for flow information that's being
 * reported to a {@link ActivityTracker}.
 * </p>
 */
public class FlowContext {
    private final InetSocketAddress clientAddress;

    public FlowContext(ClientToProxyConnection clientConnection) {
        super();
        this.clientAddress = clientConnection.getClientAddress();
    }

    /**
     * The address of the client.
     * 
     * @return
     */
    public InetSocketAddress getClientAddress() {
        return clientAddress;
    }


}
