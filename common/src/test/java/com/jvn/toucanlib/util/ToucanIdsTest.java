package com.jvn.toucanlib.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ToucanIdsTest {
    @Test
    void createsNamespacedIds() {
        ToucanIds ids = ToucanIds.create("examplemod");

        assertEquals("examplemod", ids.modId());
        assertEquals("examplemod:thing", ids.id("thing").toString());
        assertEquals("examplemod:textures/gui/icon.png", ids.texture("gui/icon.png").toString());
    }

    @Test
    void rejectsBlankModId() {
        assertThrows(IllegalArgumentException.class, () -> ToucanIds.create(""));
    }
}
