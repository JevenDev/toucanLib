package com.jvn.toucanlib.neoforge;

import com.jvn.toucanlib.toucanLib;
import net.neoforged.fml.common.Mod;

@Mod(toucanLib.MOD_ID)
public final class toucanLibNeoForge {
    public toucanLibNeoForge() {
        toucanLib.init();
    }
}
