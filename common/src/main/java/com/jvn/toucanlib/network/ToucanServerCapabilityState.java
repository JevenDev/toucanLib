package com.jvn.toucanlib.network;

import com.jvn.toucanlib.ToucanLib;
import org.slf4j.Logger;

/**
 * Tracks optional server support for a consuming mod that can run with or
 * without a ToucanLib-backed server handshake.
 */
public final class ToucanServerCapabilityState {
    private final String modId;
    private final Logger logger;
    private ToucanInstallMode mode = ToucanInstallMode.CLIENT_LOCAL_ONLY;
    private int pendingTicks;

    private ToucanServerCapabilityState(String modId, Logger logger) {
        if (modId == null || modId.isBlank()) {
            throw new IllegalArgumentException("modId must not be blank");
        }
        this.modId = modId;
        this.logger = logger == null ? ToucanLib.LOGGER : logger;
    }

    /**
     * Creates a per-mod capability state using ToucanLib's logger.
     */
    public static ToucanServerCapabilityState create(String modId) {
        return new ToucanServerCapabilityState(modId, ToucanLib.LOGGER);
    }

    /**
     * Creates a per-mod capability state using the caller's logger.
     */
    public static ToucanServerCapabilityState create(String modId, Logger logger) {
        return new ToucanServerCapabilityState(modId, logger);
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
        setMode(ToucanInstallMode.UNKNOWN_HANDSHAKE_PENDING, "waiting for server handshake");
    }

    /**
     * Advances fallback timing while a handshake is pending.
     */
    public void clientTick() {
        if (mode != ToucanInstallMode.UNKNOWN_HANDSHAKE_PENDING) {
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
                integratedServer ? ToucanInstallMode.INTEGRATED_SERVER_AUTHORITATIVE : ToucanInstallMode.SERVER_AUTHORITATIVE,
                integratedServer ? "integrated server has " + modId : "remote server has " + modId
        );
    }

    /**
     * Switches to client-only fallback mode.
     */
    public void useClientOnlyFallback(String reason) {
        pendingTicks = 0;
        setMode(ToucanInstallMode.CLIENT_LOCAL_ONLY, reason);
    }

    /**
     * Clears state on disconnect.
     */
    public void clear() {
        pendingTicks = 0;
        setMode(ToucanInstallMode.CLIENT_LOCAL_ONLY, "client disconnected");
    }

    /**
     * Returns the current install mode.
     */
    public ToucanInstallMode mode() {
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

    private void setMode(ToucanInstallMode mode, String reason) {
        if (this.mode == mode) {
            return;
        }
        this.mode = mode;
        logger.info("{} install mode: {} ({})", modId, mode, reason);
    }
}
