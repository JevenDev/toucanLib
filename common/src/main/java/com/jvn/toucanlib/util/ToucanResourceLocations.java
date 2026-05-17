package com.jvn.toucanlib.util;

import net.minecraft.resources.ResourceLocation;

/**
 * Public resource location factory helpers.
 *
 * <p>This is the low-level utility behind {@link ToucanIds}. Most consumers
 * should use {@code ToucanIds} when they repeatedly create ids for one mod id.</p>
 */
public final class ToucanResourceLocations {
    private ToucanResourceLocations() {
    }

    /**
     * Creates a resource location from a mod id and path.
     *
     * @param modId namespace for the resource location
     * @param path path within the namespace
     * @return a resource location using the supplied namespace and path
     */
    public static ResourceLocation id(String modId, String path) {
        return ResourceLocation.fromNamespaceAndPath(modId, path);
    }
}
