package com.jvn.toucanlib.neoforge.network;

import com.jvn.toucanlib.network.toucanSafeClientHandler;
import java.util.function.Consumer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class toucanNetwork {
    private final String modId;
    private final PayloadRegistrar registrar;

    private toucanNetwork(String modId, PayloadRegistrar registrar) {
        if (modId == null || modId.isBlank()) {
            throw new IllegalArgumentException("modId must not be blank");
        }
        this.modId = modId;
        this.registrar = registrar;
    }

    /**
     * Creates a network wrapper around a versioned NeoForge payload registrar.
     */
    public static toucanNetwork create(String modId, String version, RegisterPayloadHandlersEvent event) {
        if (version == null || version.isBlank()) {
            throw new IllegalArgumentException("version must not be blank");
        }
        return new toucanNetwork(modId, event.registrar(version));
    }

    /**
     * Returns a wrapper around an optional payload registrar.
     */
    public toucanNetwork optional() {
        return new toucanNetwork(modId, registrar.optional());
    }

    /**
     * Registers a serverbound play payload.
     */
    public <T extends CustomPacketPayload> void playToServer(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            IPayloadHandler<T> handler
    ) {
        registrar.playToServer(type, codec, handler);
    }

    /**
     * Registers a clientbound play payload.
     */
    public <T extends CustomPacketPayload> void playToClient(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            IPayloadHandler<T> handler
    ) {
        registrar.playToClient(type, codec, handler);
    }

    /**
     * Registers a clientbound payload that dispatches to a static client method by reflection.
     */
    public <T extends CustomPacketPayload> void safePlayToClient(
            CustomPacketPayload.Type<T> type,
            StreamCodec<? super RegistryFriendlyByteBuf, T> codec,
            String className,
            String methodName
    ) {
        playToClient(type, codec, (payload, context) -> toucanSafeClientHandler.dispatch(modId, payload, className, methodName));
    }

    /**
     * Enqueues work on the payload context.
     */
    public static void enqueue(IPayloadContext context, Runnable work) {
        context.enqueueWork(work);
    }

    /**
     * Runs the consumer only when the payload sender is a server player.
     */
    public static boolean withServerPlayer(IPayloadContext context, Consumer<ServerPlayer> consumer) {
        if (context.player() instanceof ServerPlayer serverPlayer) {
            consumer.accept(serverPlayer);
            return true;
        }
        return false;
    }
}
