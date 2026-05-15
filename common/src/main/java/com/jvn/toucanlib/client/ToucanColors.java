package com.jvn.toucanlib.client;

import net.minecraft.util.Mth;

/**
 * Color helpers for packed ARGB/RGB integer values.
 */
public final class ToucanColors {
    private ToucanColors() {
    }

    /**
     * Replaces the alpha channel of an ARGB or RGB color.
     */
    public static int withAlpha(int color, int alpha) {
        return (Mth.clamp(alpha, 0, 255) << 24) | (color & 0x00FFFFFF);
    }

    /**
     * Replaces the alpha channel of an ARGB or RGB color from a 0..1 alpha value.
     */
    public static int withAlpha(int color, float alpha) {
        return withAlpha(color, alphaByte(alpha));
    }

    /**
     * Multiplies the color's existing alpha channel by a 0..1 alpha value.
     */
    public static int multiplyAlpha(int color, float alpha) {
        int baseAlpha = (color >>> 24) & 0xFF;
        return withAlpha(color, Math.round(baseAlpha * ToucanEasing.clamp01(alpha)));
    }

    /**
     * Converts a 0..1 alpha value to a byte in the range 0..255.
     */
    public static int alphaByte(float alpha) {
        return Mth.clamp(Math.round(ToucanEasing.clamp01(alpha) * 255.0F), 0, 255);
    }

    /**
     * Linearly interpolates RGB channels while preserving the first color's alpha.
     */
    public static int lerpRgb(int from, int to, float progress) {
        float t = ToucanEasing.clamp01(progress);
        int a = (from >>> 24) & 0xFF;
        int r = Mth.lerpInt(t, (from >>> 16) & 0xFF, (to >>> 16) & 0xFF);
        int g = Mth.lerpInt(t, (from >>> 8) & 0xFF, (to >>> 8) & 0xFF);
        int b = Mth.lerpInt(t, from & 0xFF, to & 0xFF);
        return a << 24 | r << 16 | g << 8 | b;
    }

    /**
     * Linearly interpolates ARGB channels.
     */
    public static int lerpArgb(int from, int to, float progress) {
        float t = ToucanEasing.clamp01(progress);
        int a = Mth.lerpInt(t, (from >>> 24) & 0xFF, (to >>> 24) & 0xFF);
        int r = Mth.lerpInt(t, (from >>> 16) & 0xFF, (to >>> 16) & 0xFF);
        int g = Mth.lerpInt(t, (from >>> 8) & 0xFF, (to >>> 8) & 0xFF);
        int b = Mth.lerpInt(t, from & 0xFF, to & 0xFF);
        return a << 24 | r << 16 | g << 8 | b;
    }
}
