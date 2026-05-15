package com.jvn.toucanlib.network;

/**
 * Describes whether a consuming mod should trust server-side state or use a
 * client-only fallback.
 */
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
