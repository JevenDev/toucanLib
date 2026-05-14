package com.jvn.toucanlib.network;

import com.jvn.toucanlib.toucanLib;
import com.jvn.toucanlib.client.toucanClientOnly;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public final class toucanSafeClientHandler {
    private toucanSafeClientHandler() {
    }

    /**
     * Dispatches a payload to a public static client handler without loading the handler on a dedicated server.
     */
    public static <T extends CustomPacketPayload> boolean dispatch(String modId, T payload, String className, String methodName) {
        if (payload == null) {
            throw new IllegalArgumentException("payload must not be null");
        }
        boolean invoked = toucanClientOnly.safeInvokeStatic(className, methodName, new Class<?>[]{payload.getClass()}, payload);
        if (!invoked && toucanClientOnly.isClient()) {
            toucanLib.LOGGER.warn("Unable to dispatch {} client payload {} to {}#{}",
                    modId, payload.getClass().getSimpleName(), className, methodName);
        }
        return invoked;
    }
}
