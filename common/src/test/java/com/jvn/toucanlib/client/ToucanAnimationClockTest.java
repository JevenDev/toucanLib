package com.jvn.toucanlib.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class toucanAnimationClockTest {
    @Test
    void ticksOnlyAdvanceWhenUnpaused() {
        toucanAnimationClock clock = new toucanAnimationClock();

        clock.tick(false);
        clock.tick(true);
        clock.tick(false);

        assertEquals(2, clock.ticks());
    }

    @Test
    void progressIsClampedToDuration() {
        toucanAnimationClock clock = new toucanAnimationClock();

        clock.setTicks(15);

        assertEquals(0.75F, clock.progress(20));
        assertEquals(1.0F, clock.progress(10));
        assertEquals(1.0F, clock.progress(0));
    }
}
