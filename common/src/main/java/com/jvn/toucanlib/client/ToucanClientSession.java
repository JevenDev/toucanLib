package com.jvn.toucanlib.client;

import net.minecraft.client.Minecraft;

public final class ToucanClientSession {
    private boolean inWorld;
    private int ticksInSession;

    /**
     * Updates session state from the current Minecraft client.
     */
    public ToucanClientSessionUpdate tick(Minecraft minecraft) {
        return tick(minecraft != null && minecraft.player != null && minecraft.level != null);
    }

    /**
     * Updates session state from a caller-provided in-world flag.
     */
    public ToucanClientSessionUpdate tick(boolean currentlyInWorld) {
        boolean enteredWorld = !inWorld && currentlyInWorld;
        boolean leftWorld = inWorld && !currentlyInWorld;
        inWorld = currentlyInWorld;

        if (!currentlyInWorld) {
            ticksInSession = 0;
        } else if (enteredWorld) {
            ticksInSession = 1;
        } else {
            ticksInSession++;
        }

        return new ToucanClientSessionUpdate(enteredWorld, leftWorld, inWorld, ticksInSession);
    }

    /**
     * Clears session state.
     */
    public void clear() {
        inWorld = false;
        ticksInSession = 0;
    }

    /**
     * Returns true when the client is currently considered in-world.
     */
    public boolean isInWorld() {
        return inWorld;
    }

    /**
     * Returns the number of ticks since the current client session began.
     */
    public int ticksInSession() {
        return ticksInSession;
    }

    public record ToucanClientSessionUpdate(boolean enteredWorld, boolean leftWorld, boolean inWorld, int ticksInSession) {
    }
}
