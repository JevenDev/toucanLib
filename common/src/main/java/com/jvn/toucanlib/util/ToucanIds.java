package com.jvn.toucanlib.util;

import net.minecraft.resources.ResourceLocation;

/**
 * Small namespaced id factory for mods that create many resource locations.
 */
public final class ToucanIds {
    private final String modId;

    private ToucanIds(String modId) {
        if (modId == null || modId.isBlank()) {
            throw new IllegalArgumentException("modId must not be blank");
        }
        this.modId = modId;
    }

    public static ToucanIds create(String modId) {
        return new ToucanIds(modId);
    }

    public String modId() {
        return modId;
    }

    public ResourceLocation id(String path) {
        return ToucanResourceLocations.id(modId, path);
    }

    public ResourceLocation texture(String path) {
        return id("textures/" + path);
    }
}
