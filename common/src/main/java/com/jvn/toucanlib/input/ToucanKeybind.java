package com.jvn.toucanlib.input;

import net.minecraft.client.KeyMapping;

/**
 * Named key mapping remembered by {@link ToucanKeybinds}.
 */
public record ToucanKeybind(String name, KeyMapping mapping) {
    public ToucanKeybind {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (mapping == null) {
            throw new IllegalArgumentException("mapping must not be null");
        }
    }
}
