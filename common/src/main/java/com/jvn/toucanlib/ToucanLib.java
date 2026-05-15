package com.jvn.toucanlib;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

/**
 * Shared ToucanLib constants and loader-neutral initialization.
 */
public final class ToucanLib {
    /**
     * The canonical mod id used by resources, metadata, and platform entrypoints.
     */
    public static final String MOD_ID = "toucanlib";

    /**
     * Logger for ToucanLib internals. Consuming mods should normally keep their own logger.
     */
    public static final Logger LOGGER = LogUtils.getLogger();

    private ToucanLib() {
    }

    public static void init() {
        LOGGER.debug("Initializing toucanLib");
    }
}
