package com.jvn.toucanlib;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public final class ToucanLib {
    public static final String MOD_ID = "toucanlib";
    public static final Logger LOGGER = LogUtils.getLogger();

    private ToucanLib() {
    }

    public static void init() {
        LOGGER.debug("Initializing ToucanLib");
    }
}
