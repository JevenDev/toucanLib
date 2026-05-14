package com.jvn.toucanlib.input;

import net.minecraft.client.KeyMapping;

public record toucanKeybind(String name, KeyMapping mapping) {
    public toucanKeybind {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        if (mapping == null) {
            throw new IllegalArgumentException("mapping must not be null");
        }
    }
}
