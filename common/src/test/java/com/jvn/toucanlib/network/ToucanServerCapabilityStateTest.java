package com.jvn.toucanlib.network;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ToucanServerCapabilityStateTest {
    @Test
    void handshakeFallsBackAfterConfiguredTicks() {
        toucanServerCapabilityState state = toucanServerCapabilityState.create("sample", null);

        state.beginHandshake(2);
        state.clientTick();

        assertEquals(toucanInstallMode.UNKNOWN_HANDSHAKE_PENDING, state.mode());

        state.clientTick();

        assertEquals(toucanInstallMode.CLIENT_LOCAL_ONLY, state.mode());
        assertTrue(state.isClientOnlyFallback());
        assertFalse(state.isServerAuthoritative());
    }

    @Test
    void serverConfirmationClearsFallbackMode() {
        toucanServerCapabilityState state = toucanServerCapabilityState.create("sample", null);

        state.beginHandshake(5);
        state.confirmServerAuthoritative(false);

        assertEquals(toucanInstallMode.SERVER_AUTHORITATIVE, state.mode());
        assertTrue(state.isServerAuthoritative());
        assertFalse(state.isClientOnlyFallback());
    }

    @Test
    void rejectsBlankModId() {
        assertThrows(IllegalArgumentException.class, () -> toucanServerCapabilityState.create(" "));
    }
}
