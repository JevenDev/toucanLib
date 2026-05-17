package com.jvn.toucanlib.neoforge.event;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Small registration helpers for mods with constructor-heavy event wiring.
 */
public final class ToucanEventBuses {
    private ToucanEventBuses() {
    }

    /**
     * Wraps a NeoForge event bus for fluent listener registration.
     *
     * @param bus event bus to register listeners on
     * @return registration wrapper for {@code bus}
     */
    public static ToucanEventRegistration on(IEventBus bus) {
        return new ToucanEventRegistration(bus);
    }

    /**
     * Wraps the global NeoForge game event bus.
     *
     * @return registration wrapper for {@link NeoForge#EVENT_BUS}
     */
    public static ToucanEventRegistration game() {
        return on(NeoForge.EVENT_BUS);
    }
}
