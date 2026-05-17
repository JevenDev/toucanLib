package com.jvn.toucanlib.fabric;

import com.jvn.toucanlib.ToucanLib;
import net.fabricmc.api.ModInitializer;

/**
 * Internal Fabric loader entrypoint.
 *
 * <p>This class is public so Fabric can construct it. Consuming mods should
 * treat it as implementation detail and use the documented common helpers
 * instead.</p>
 */
public final class ToucanLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ToucanLib.init();
    }
}
