package com.jvn.toucanlib;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(ToucanLib.MOD_ID)
public final class ToucanLib {
    public static final String MOD_ID = "toucanlib";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ToucanLib(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.debug("Initializing ToucanLib");
    }
}
