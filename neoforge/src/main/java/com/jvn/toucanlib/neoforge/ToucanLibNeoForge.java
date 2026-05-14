package com.jvn.toucanlib.neoforge;

import com.jvn.toucanlib.ToucanLib;
import net.neoforged.fml.common.Mod;

@Mod(ToucanLib.MOD_ID)
public final class ToucanLibNeoForge {
    public ToucanLibNeoForge() {
        ToucanLib.init();
    }
}
