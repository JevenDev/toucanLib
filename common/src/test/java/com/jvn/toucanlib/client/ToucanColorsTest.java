package com.jvn.toucanlib.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ToucanColorsTest {
    @Test
    void withAlphaClampsAndPreservesRgb() {
        assertEquals(0x00_12_34_56, ToucanColors.withAlpha(0xAA_12_34_56, -20));
        assertEquals(0x7F_12_34_56, ToucanColors.withAlpha(0xAA_12_34_56, 127));
        assertEquals(0xFF_12_34_56, ToucanColors.withAlpha(0xAA_12_34_56, 999));
    }

    @Test
    void alphaByteConvertsNormalizedAlpha() {
        assertEquals(0, ToucanColors.alphaByte(-1.0F));
        assertEquals(128, ToucanColors.alphaByte(0.5F));
        assertEquals(255, ToucanColors.alphaByte(2.0F));
    }

    @Test
    void lerpArgbInterpolatesAllChannels() {
        assertEquals(0x7F_7F_7F_7F, ToucanColors.lerpArgb(0x00_00_00_00, 0xFF_FF_FF_FF, 0.5F));
    }
}
