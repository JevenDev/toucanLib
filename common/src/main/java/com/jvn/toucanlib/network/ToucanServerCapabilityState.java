package com.jvn.toucanlib.network;

import com.jvn.toucanlib.toucanLib;
import org.slf4j.Logger;

public final class toucanServerCapabilityState {
    private final String modId;
    private final Logger logger;
    private toucanInstallMode mode = toucanInstallMode.CLIENT_LOCAL_ONLY;
    private int pendingTicks;

    private toucanServerCapabilityState(String modId, Logger logger) {
        if (modId == null || modId.isBlank()) {
            throw new IllegalArgumentException("modId must not be blank");
        }
        this.modId = modId;
        this.logger = logger == null ? toucanLib.LOGGER : logger;
    }

    /**
     * Creates a per-mod capability state using ToucanLib's logger.
     */
    public static toucanServerCapabilityState create(String modId) {
        return new toucanServerCapabilityState(modId, toucanLib.LOGGER);
    }

    /**
     * Creates a per-mod capability state using the caller's logger.
     */
    public static toucanServerCapabilityState create(String modId, Logger logger) {
        return new toucanServerCapabilityState(modId, logger);
    }

    /**
     * Starts waiting for a server handshake.
     */
    public void beginHandshake() {
        beginHandshake(0);
    }

    /**
     * Starts waiting for a server handshake and enables tick-based fallback.
     */
    public void beginHandshake(int fallbackTicks) {
        pendingTicks = Math.max(0, fallbackTicks);
        setMode(toucanInstallMode.UNKNOWN_HANDSHAKE_PENDING, "waiting for server handshake");
    }

    /**
     * Advances fallback timing while a handshake is pending.
     */
    public void clientTick() {
        if (mode != toucanInstallMode.UNKNOWN_HANDSHAKE_PENDING) {
            pendingTicks = 0;
            return;
        }
        if (pendingTicks > 0) {
            pendingTicks--;
        }
        if (pendingTicks == 0) {
            useClientOnlyFallback("server handshake not received");
        }
    }

    /**
     * Marks the server as authoritative.
     */
    public void confirmServerAuthoritative(boolean integratedServer) {
        pendingTicks = 0;
        setMode(
                integratedServer ? toucanInstallMode.INTEGRATED_SERVER_AUTHORITATIVE : toucanInstallMode.SERVER_AUTHORITATIVE,
                integratedServer ? "integrated server has " + modId : "remote server has " + modId
        );
    }

    /**
     * Switches to client-only fallback mode.
     */
    public void useClientOnlyFallback(String reason) {
        pendingTicks = 0;
        setMode(toucanInstallMode.CLIENT_LOCAL_ONLY, reason);
    }

    /**
     * Clears state on disconnect.
     */
    public void clear() {
        pendingTicks = 0;
        setMode(toucanInstallMode.CLIENT_LOCAL_ONLY, "client disconnected");
    }

    /**
     * Returns the current install mode.
     */
    public toucanInstallMode mode() {
        return mode;
    }

    /**
     * Returns true when server-side state is authoritative.
     */
    public boolean isServerAuthoritative() {
        return mode.serverAuthoritative();
    }

    /**
     * Returns true when client-only fallback behavior is active.
     */
    public boolean isClientOnlyFallback() {
        return mode.clientOnlyFallback();
    }

    private void setMode(toucanInstallMode mode, String reason) {
        if (this.mode == mode) {
            return;
        }
        this.mode = mode;
        logger.info("{} install mode: {} ({})", modId, mode, reason);
    }
}
