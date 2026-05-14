package com.jvn.toucanlib.neoforge.event;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Small registration helpers for mods with constructor-heavy event wiring.
 */
public final class ToucanEventBuses {
    private ToucanEventBuses() {
    }

    public static ToucanEventRegistration on(IEventBus bus) {
        return new ToucanEventRegistration(bus);
    }

    public static ToucanEventRegistration game() {
        return on(NeoForge.EVENT_BUS);
    }
}
