package com.jvn.toucanlib.util;

import net.minecraft.resources.ResourceLocation;

/**
 * Small namespaced resource id factory for mods that create many resource locations.
 */
public final class ToucanIds {
    private final String modId;

    private ToucanIds(String modId) {
        if (modId == null || modId.isBlank()) {
            throw new IllegalArgumentException("modId must not be blank");
        }
        this.modId = modId;
    }

    /**
     * Creates a new id factory for the given mod id.
     *
     * @param modId namespace to use for all ids created by this factory
     * @return a new id factory for {@code modId}
     */
    public static ToucanIds create(String modId) {
        return new ToucanIds(modId);
    }

    /**
     * Returns the namespace used by this factory.
     */
    public String modId() {
        return modId;
    }

    /**
     * Creates an id in this factory's namespace.
     *
     * @param path resource path without the namespace
     * @return a resource location in this factory's namespace
     */
    public ResourceLocation id(String path) {
        return ToucanResourceLocations.id(modId, path);
    }

    /**
     * Creates a texture id under the {@code textures/} directory.
     *
     * @param path texture path relative to {@code textures/}
     * @return a resource location for the texture path
     */
    public ResourceLocation texture(String path) {
        return id("textures/" + path);
    }
}
