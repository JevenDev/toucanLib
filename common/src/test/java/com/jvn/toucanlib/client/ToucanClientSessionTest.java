package com.jvn.toucanlib.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jvn.toucanlib.client.ToucanClientSession.ToucanClientSessionUpdate;
import org.junit.jupiter.api.Test;

class toucanClientSessionTest {
    @Test
    void reportsEnterStayAndLeaveTransitions() {
        ToucanClientSession session = new ToucanClientSession();

        ToucanClientSessionUpdate enter = session.tick(true);
        ToucanClientSessionUpdate stay = session.tick(true);
        ToucanClientSessionUpdate leave = session.tick(false);

        assertTrue(enter.enteredWorld());
        assertFalse(enter.leftWorld());
        assertEquals(1, enter.ticksInSession());

        assertFalse(stay.enteredWorld());
        assertTrue(stay.inWorld());
        assertEquals(2, stay.ticksInSession());

        assertFalse(leave.enteredWorld());
        assertTrue(leave.leftWorld());
        assertFalse(leave.inWorld());
        assertEquals(0, leave.ticksInSession());
    }
}
