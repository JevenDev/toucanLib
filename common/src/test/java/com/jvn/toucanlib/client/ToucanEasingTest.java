package com.jvn.toucanlib.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class toucanEasingTest {
    @Test
    void clamp01BoundsProgress() {
        assertEquals(0.0F, toucanEasing.clamp01(-0.25F));
        assertEquals(0.5F, toucanEasing.clamp01(0.5F));
        assertEquals(1.0F, toucanEasing.clamp01(1.25F));
    }

    @Test
    void smoothstepHasStableEndpointsAndMidpoint() {
        assertEquals(0.0F, toucanEasing.smoothstep(0.0F));
        assertEquals(0.5F, toucanEasing.smoothstep(0.5F));
        assertEquals(1.0F, toucanEasing.smoothstep(1.0F));
    }
}
