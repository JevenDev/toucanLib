package com.jvn.toucanlib.neoforge.config;

import java.util.function.BiFunction;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public final class toucanConfigScreens {
    private toucanConfigScreens() {
    }

    /**
     * Registers a NeoForge config screen factory.
     */
    public static void register(ModContainer modContainer, IConfigScreenFactory factory) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, factory);
    }

    /**
     * Registers a config screen factory from a simple bi-function.
     */
    public static void register(ModContainer modContainer, BiFunction<ModContainer, Screen, Screen> factory) {
        register(modContainer, (IConfigScreenFactory) factory::apply);
    }
}
