package com.jvn.toucanlib.client;

import net.minecraft.util.Mth;

public final class toucanEasing {
    private toucanEasing() {
    }

    /**
     * Cubic ease-out in the range 0..1.
     */
    public static float easeOutCubic(float progress) {
        float t = clamp01(progress);
        return 1.0F - (float) Math.pow(1.0F - t, 3.0D);
    }

    /**
     * Sine ease-in in the range 0..1.
     */
    public static float sineIn(float progress) {
        float t = clamp01(progress);
        return 1.0F - Mth.cos(t * Mth.HALF_PI);
    }

    /**
     * Sine ease-out in the range 0..1.
     */
    public static float sineOut(float progress) {
        return Mth.sin(clamp01(progress) * Mth.HALF_PI);
    }

    /**
     * Symmetric ease-in-out in the range 0..1.
     */
    public static float easeInOut(float progress) {
        return smoothstep(progress);
    }

    /**
     * Back ease-out with caller-controlled overshoot strength.
     */
    public static float easeOutBack(float progress, float strength) {
        float t = clamp01(progress) - 1.0F;
        return 1.0F + (strength + 1.0F) * t * t * t + strength * t * t;
    }

    /**
     * Smooth Hermite interpolation in the range 0..1.
     */
    public static float smoothstep(float progress) {
        float t = clamp01(progress);
        return t * t * (3.0F - 2.0F * t);
    }

    /**
     * Clamps a progress value to the range 0..1.
     */
    public static float clamp01(float progress) {
        return Mth.clamp(progress, 0.0F, 1.0F);
    }
}
