package com.jvn.toucanlib.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ToucanMotionTest {
    @Test
    void approachMovesTowardTargetWithoutOvershooting() {
        assertEquals(3.0F, ToucanMotion.approach(0.0F, 10.0F, 3.0F));
        assertEquals(7.0F, ToucanMotion.approach(10.0F, 0.0F, 3.0F));
        assertEquals(10.0F, ToucanMotion.approach(8.0F, 10.0F, 5.0F));
    }

    @Test
    void approachRejectsNegativeStep() {
        assertThrows(IllegalArgumentException.class, () -> ToucanMotion.approach(0.0F, 1.0F, -0.1F));
    }

    @Test
    void loopHandlesDurationAndNegativePhase() {
        assertEquals(0.0F, ToucanMotion.loop(5.0F, 0.0F, 0.0F));
        assertEquals(0.25F, ToucanMotion.loop(2.5F, 10.0F, 0.0F));
        assertEquals(0.75F, ToucanMotion.loop(-2.5F, 10.0F, 0.0F));
    }
}
