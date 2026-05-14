package com.jvn.toucanlib.util;

import net.minecraft.resources.ResourceLocation;

public final class ToucanResourceLocations {
    private ToucanResourceLocations() {
    }

    /**
     * Creates a resource location from a mod id and path.
     */
    public static ResourceLocation id(String modId, String path) {
        return ResourceLocation.fromNamespaceAndPath(modId, path);
    }
}
