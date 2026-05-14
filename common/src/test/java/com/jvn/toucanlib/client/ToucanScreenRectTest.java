package com.jvn.toucanlib.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class toucanScreenRectTest {
    @Test
    void exposesEdgesCentersAndExclusiveContainment() {
        ToucanScreenRect rect = new ToucanScreenRect(10, 20, 30, 40);

        assertEquals(40, rect.right());
        assertEquals(60, rect.bottom());
        assertEquals(25, rect.centerX());
        assertEquals(40, rect.centerY());
        assertTrue(rect.contains(10, 20));
        assertFalse(rect.contains(40, 20));
        assertFalse(rect.contains(10, 60));
    }

    @Test
    void rejectsNegativeSize() {
        assertThrows(IllegalArgumentException.class, () -> new ToucanScreenRect(0, 0, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> new ToucanScreenRect(0, 0, 1, -1));
    }
}
