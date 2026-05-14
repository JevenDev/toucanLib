package com.jvn.toucanlib.fabric;

import com.jvn.toucanlib.ToucanLib;
import net.fabricmc.api.ModInitializer;

public final class ToucanLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ToucanLib.init();
    }
}
