package com.jvn.toucanlib.neoforge;

import com.jvn.toucanlib.ToucanLib;
import net.neoforged.fml.common.Mod;

/**
 * Internal NeoForge loader entrypoint.
 *
 * <p>This class is public so NeoForge can construct it. Consuming mods should
 * treat it as implementation detail and use the documented common or NeoForge
 * helper packages instead.</p>
 */
@Mod(ToucanLib.MOD_ID)
public final class ToucanLibNeoForge {
    public ToucanLibNeoForge() {
        ToucanLib.init();
    }
}
