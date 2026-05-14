package com.jvn.toucanlib.client;

import net.minecraft.util.Mth;

public final class ToucanEasing {
    private ToucanEasing() {
    }

    /**
     * Cubic ease-out in the range 0..1.
     */
    public static float easeOutCubic(float progress) {
        float t = clamp01(progress);
        return 1.0F - (float) Math.pow(1.0F - t, 3.0D);
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
