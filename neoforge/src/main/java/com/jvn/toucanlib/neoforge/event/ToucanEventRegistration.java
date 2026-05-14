package com.jvn.toucanlib.neoforge.event;

import java.util.function.Consumer;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;

public final class ToucanEventRegistration {
    private final IEventBus bus;

    ToucanEventRegistration(IEventBus bus) {
        this.bus = bus;
    }

    public <T extends Event> ToucanEventRegistration listener(Consumer<T> listener) {
        bus.addListener(listener);
        return this;
    }
}
