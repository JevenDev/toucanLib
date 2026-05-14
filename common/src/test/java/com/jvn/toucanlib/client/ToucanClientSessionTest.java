package com.jvn.toucanlib.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jvn.toucanlib.client.toucanClientSession.toucanClientSessionUpdate;
import org.junit.jupiter.api.Test;

class toucanClientSessionTest {
    @Test
    void reportsEnterStayAndLeaveTransitions() {
        toucanClientSession session = new toucanClientSession();

        toucanClientSessionUpdate enter = session.tick(true);
        toucanClientSessionUpdate stay = session.tick(true);
        toucanClientSessionUpdate leave = session.tick(false);

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
