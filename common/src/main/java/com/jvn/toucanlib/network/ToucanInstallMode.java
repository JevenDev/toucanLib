package com.jvn.toucanlib.network;

public enum ToucanInstallMode {
    UNKNOWN_HANDSHAKE_PENDING,
    CLIENT_LOCAL_ONLY,
    SERVER_AUTHORITATIVE,
    INTEGRATED_SERVER_AUTHORITATIVE;

    /**
     * Returns true when server-side state is expected to be authoritative.
     */
    public boolean serverAuthoritative() {
        return this == SERVER_AUTHORITATIVE || this == INTEGRATED_SERVER_AUTHORITATIVE;
    }

    /**
     * Returns true when the client is using local fallback behavior.
     */
    public boolean clientOnlyFallback() {
        return this == CLIENT_LOCAL_ONLY;
    }
}
