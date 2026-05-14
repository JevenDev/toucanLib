package com.jvn.toucanlib.client;

import com.jvn.toucanlib.ToucanLib;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public final class ToucanClientOnly {
    private ToucanClientOnly() {
    }

    /**
     * Returns true when the current physical distribution is the client.
     */
    public static boolean isClient() {
        return Platform.getEnvironment() == Env.CLIENT;
    }

    /**
     * Runs the action only on the physical client.
     */
    public static void run(Runnable action) {
        Objects.requireNonNull(action, "action");
        if (isClient()) {
            action.run();
        }
    }

    /**
     * Fails fast when a client-only helper is used outside the physical client.
     */
    public static void requireClient(String feature) {
        if (!isClient()) {
            throw new IllegalStateException(feature + " can only be used on the physical client");
        }
    }

    /**
     * Invokes a public static method by name only on the physical client.
     *
     * @return true when the method was invoked successfully
     */
    public static boolean safeInvokeStatic(String className, String methodName, Class<?>[] paramTypes, Object... args) {
        Objects.requireNonNull(className, "className");
        Objects.requireNonNull(methodName, "methodName");
        Objects.requireNonNull(paramTypes, "paramTypes");
        if (!isClient()) {
            return false;
        }
        try {
            Class<?> handlerClass = Class.forName(className);
            Method method = handlerClass.getMethod(methodName, paramTypes);
            method.invoke(null, args);
            return true;
        } catch (ClassNotFoundException | NoSuchMethodException exception) {
            ToucanLib.LOGGER.warn("Unable to find client handler {}#{}", className, methodName, exception);
        } catch (IllegalAccessException exception) {
            ToucanLib.LOGGER.warn("Unable to access client handler {}#{}", className, methodName, exception);
        } catch (InvocationTargetException exception) {
            ToucanLib.LOGGER.error("Client handler {}#{} threw an exception", className, methodName, exception.getCause());
        }
        return false;
    }
}
