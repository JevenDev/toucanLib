package com.jvn.toucanlib.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class toucanMotionTest {
    @Test
    void approachMovesTowardTargetWithoutOvershooting() {
        assertEquals(3.0F, toucanMotion.approach(0.0F, 10.0F, 3.0F));
        assertEquals(7.0F, toucanMotion.approach(10.0F, 0.0F, 3.0F));
        assertEquals(10.0F, toucanMotion.approach(8.0F, 10.0F, 5.0F));
    }

    @Test
    void approachRejectsNegativeStep() {
        assertThrows(IllegalArgumentException.class, () -> toucanMotion.approach(0.0F, 1.0F, -0.1F));
    }

    @Test
    void loopHandlesDurationAndNegativePhase() {
        assertEquals(0.0F, toucanMotion.loop(5.0F, 0.0F, 0.0F));
        assertEquals(0.25F, toucanMotion.loop(2.5F, 10.0F, 0.0F));
        assertEquals(0.75F, toucanMotion.loop(-2.5F, 10.0F, 0.0F));
    }
}
