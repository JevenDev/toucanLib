package com.jvn.toucanlib.fabric;

import com.jvn.toucanlib.toucanLib;
import net.fabricmc.api.ModInitializer;

public final class toucanLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        toucanLib.init();
    }
}
