package com.jvn.toucanlib.neoforge.event;

import java.util.function.Consumer;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;

/**
 * Fluent wrapper for registering multiple listeners on the same NeoForge event bus.
 */
public final class ToucanEventRegistration {
    private final IEventBus bus;

    ToucanEventRegistration(IEventBus bus) {
        this.bus = bus;
    }

    /**
     * Adds a listener to the wrapped bus and returns this wrapper for chaining.
     */
    public <T extends Event> ToucanEventRegistration listener(Consumer<T> listener) {
        bus.addListener(listener);
        return this;
    }
}
