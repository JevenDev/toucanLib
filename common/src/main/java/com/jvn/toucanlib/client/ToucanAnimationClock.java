package com.jvn.toucanlib.client;

import net.minecraft.util.Mth;

public final class ToucanAnimationClock {
    private int ticks;

    /**
     * Advances the clock by one tick unless paused.
     */
    public void tick(boolean paused) {
        if (!paused) {
            ticks++;
        }
    }

    /**
     * Resets the clock to zero ticks.
     */
    public void reset() {
        ticks = 0;
    }

    /**
     * Sets the current tick count.
     */
    public void setTicks(int ticks) {
        this.ticks = Math.max(0, ticks);
    }

    /**
     * Returns the current tick count.
     */
    public int ticks() {
        return ticks;
    }

    /**
     * Returns progress through a duration as a clamped 0..1 value.
     */
    public float progress(int durationTicks) {
        if (durationTicks <= 0) {
            return 1.0F;
        }
        return Mth.clamp(ticks / (float) durationTicks, 0.0F, 1.0F);
    }
}
