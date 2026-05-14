package com.jvn.toucanlib.client;

import com.jvn.toucanlib.util.ToucanResourceLocations;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public final class ToucanGuiLayers {
    private ToucanGuiLayers() {
    }

    /**
     * Registers a GUI layer below all others.
     */
    public static ResourceLocation registerBelowAll(RegisterGuiLayersEvent event, String modId, String path, LayeredDraw.Layer layer) {
        ResourceLocation id = ToucanResourceLocations.id(modId, path);
        event.registerBelowAll(id, layer);
        return id;
    }

    /**
     * Registers a GUI layer below another layer.
     */
    public static ResourceLocation registerBelow(RegisterGuiLayersEvent event, ResourceLocation other, String modId, String path, LayeredDraw.Layer layer) {
        ResourceLocation id = ToucanResourceLocations.id(modId, path);
        event.registerBelow(other, id, layer);
        return id;
    }

    /**
     * Registers a GUI layer above another layer.
     */
    public static ResourceLocation registerAbove(RegisterGuiLayersEvent event, ResourceLocation other, String modId, String path, LayeredDraw.Layer layer) {
        ResourceLocation id = ToucanResourceLocations.id(modId, path);
        event.registerAbove(other, id, layer);
        return id;
    }

    /**
     * Registers a GUI layer above all others.
     */
    public static ResourceLocation registerAboveAll(RegisterGuiLayersEvent event, String modId, String path, LayeredDraw.Layer layer) {
        ResourceLocation id = ToucanResourceLocations.id(modId, path);
        event.registerAboveAll(id, layer);
        return id;
    }
}
