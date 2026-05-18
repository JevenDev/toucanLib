package com.jvn.toucanlib.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.joml.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * Small manager and renderer for animated in-world text indicators.
 *
 * <p>Each mod should create its own instance, add entries from client-side code,
 * and call {@link #render(Minecraft, PoseStack, Vec3, float, long)} from the
 * appropriate world render event. This class only renders text; gameplay
 * decisions, particles, colors, and presets stay with the consuming mod.</p>
 */
public final class ToucanWorldTextIndicators {
    private static final int NO_ANCHOR_ENTITY_ID = -1;
    private static final double SPREAD_ANGLE_STEP = Math.PI * (3.0D - Math.sqrt(5.0D));

    private final List<Entry> entries = new ArrayList<>();
    private int spreadSequence;

    /**
     * Removes all active indicators.
     */
    public void clear() {
        synchronized (entries) {
            entries.clear();
        }
    }

    /**
     * Returns the number of active indicators.
     */
    public int size() {
        synchronized (entries) {
            return entries.size();
        }
    }

    /**
     * Adds a floating entry with randomized scatter and drift.
     *
     * @param start world-space origin
     * @param style visual style
     * @param nowMillis current animation time
     */
    public void addFloating(Vec3 start, ToucanWorldTextStyle style, long nowMillis) {
        synchronized (entries) {
            double offsetAngle = spreadSequence++ * SPREAD_ANGLE_STEP + (Math.random() - 0.5D) * 0.46D;
            double offsetRadius = style.scatterRadius() * (0.82D + Math.random() * 0.38D);
            Vec3 offset = new Vec3(Math.cos(offsetAngle) * offsetRadius, 0.0D, Math.sin(offsetAngle) * offsetRadius);
            Vec3 drift = new Vec3(Math.cos(offsetAngle) * style.driftRadius(), style.riseDistance(), Math.sin(offsetAngle) * style.driftRadius());
            entries.add(new Entry(
                    start.add(offset),
                    drift,
                    style.label(),
                    style.color(),
                    style.italic(),
                    (float) (Math.random() * Mth.TWO_PI),
                    Math.random() < 0.5D ? -1.0F : 1.0F,
                    nowMillis,
                    style.lifetimeMillis(),
                    style.scale(),
                    style.popStrength(),
                    style.hoverAmplitude(),
                    style.swayAmplitude(),
                    style.tiltDegrees(),
                    0.0D,
                    NO_ANCHOR_ENTITY_ID,
                    0.0D));
        }
    }

    /**
     * Adds an entity-anchored entry that rises above the entity.
     *
     * @param entity entity to follow while alive
     * @param offset offset from the entity position
     * @param style visual style
     * @param nowMillis current animation time
     */
    public void addAnchored(Entity entity, Vec3 offset, ToucanWorldTextStyle style, long nowMillis) {
        addAnchoredDirected(
                entity,
                offset,
                new Vec3(0.0D, style.riseDistance(), 0.0D),
                style,
                Math.random() < 0.5D ? -1.0F : 1.0F,
                (float) (Math.random() * Mth.TWO_PI),
                0.08D,
                0.0D,
                nowMillis);
    }

    /**
     * Adds an entity-anchored entry with explicit drift, phase, and arc values.
     *
     * @param entity entity to follow while alive
     * @param offset offset from the entity position
     * @param drift movement over the entry lifetime
     * @param style visual style
     * @param direction local tilt direction, usually {@code -1} or {@code 1}
     * @param phase animation phase in radians
     * @param arcHeight extra vertical arc height
     * @param anchorYOffsetScale entity height multiplier added to the anchor
     * @param nowMillis current animation time
     */
    public void addAnchoredDirected(
            Entity entity,
            Vec3 offset,
            Vec3 drift,
            ToucanWorldTextStyle style,
            float direction,
            float phase,
            double arcHeight,
            double anchorYOffsetScale,
            long nowMillis) {
        synchronized (entries) {
            entries.add(new Entry(
                    offset,
                    drift,
                    style.label(),
                    style.color(),
                    style.italic(),
                    phase,
                    direction,
                    nowMillis,
                    style.lifetimeMillis(),
                    style.scale(),
                    style.popStrength(),
                    style.hoverAmplitude(),
                    style.swayAmplitude(),
                    style.tiltDegrees(),
                    arcHeight,
                    entity.getId(),
                    anchorYOffsetScale));
        }
    }

    /**
     * Renders active indicators and removes expired entries.
     *
     * @param minecraft Minecraft client instance
     * @param poseStack active pose stack
     * @param cameraPosition world-space camera position
     * @param partialTick current partial tick
     * @param nowMillis current animation time
     * @return number of indicators rendered
     */
    public int render(Minecraft minecraft, PoseStack poseStack, Vec3 cameraPosition, float partialTick, long nowMillis) {
        List<Entry> activeEntries = collectActiveEntries(nowMillis);
        if (activeEntries.isEmpty()) {
            return 0;
        }

        Font font = minecraft.font;
        MultiBufferSource.BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
        int rendered = 0;
        for (Entry entry : activeEntries) {
            if (renderEntry(minecraft, font, bufferSource, poseStack, cameraPosition, entry, nowMillis, partialTick)) {
                rendered++;
            }
        }
        bufferSource.endBatch();
        return rendered;
    }

    /**
     * Draws full-bright billboard text with Minecraft's eight-way outline stroke.
     *
     * @param font font used for measurement and drawing
     * @param bufferSource render buffer
     * @param matrix current pose matrix
     * @param text text to draw
     * @param x left coordinate in local text space
     * @param y top coordinate in local text space
     * @param color packed ARGB text color
     * @param italic whether the text should render italic
     */
    public static void drawOutlinedText(
            Font font,
            MultiBufferSource bufferSource,
            Matrix4f matrix,
            String text,
            float x,
            float y,
            int color,
            boolean italic) {
        Component component = Component.literal(text);
        if (italic) {
            component = component.copy().withStyle(Style.EMPTY.withItalic(true));
        }
        font.drawInBatch8xOutline(
                component.getVisualOrderText(),
                x,
                y,
                color,
                ToucanColors.withAlpha(0xFF000000, ((color >>> 24) & 0xFF) / 255.0F),
                matrix,
                bufferSource,
                LightTexture.FULL_BRIGHT);
    }

    private List<Entry> collectActiveEntries(long nowMillis) {
        List<Entry> activeEntries = new ArrayList<>();

        synchronized (entries) {
            Iterator<Entry> iterator = entries.iterator();
            while (iterator.hasNext()) {
                Entry entry = iterator.next();
                if (entry.isExpired(nowMillis)) {
                    iterator.remove();
                    continue;
                }
                activeEntries.add(entry);
            }
        }

        return activeEntries;
    }

    private boolean renderEntry(
            Minecraft minecraft,
            Font font,
            MultiBufferSource.BufferSource bufferSource,
            PoseStack poseStack,
            Vec3 cameraPosition,
            Entry entry,
            long nowMillis,
            float partialTick) {
        float progress = entry.progress(nowMillis);
        boolean anchored = entry.anchorEntityId() != NO_ANCHOR_ENTITY_ID;
        float easedRise = anchored ? ToucanEasing.easeOutCubic(progress) : 1.0F - (1.0F - progress) * (1.0F - progress);
        float intro = Mth.clamp(progress / 0.16F, 0.0F, 1.0F);
        float settle = Mth.clamp((progress - 0.12F) / 0.88F, 0.0F, 1.0F);
        float fadeOut = anchored ? Mth.clamp((progress - 0.48F) / 0.52F, 0.0F, 1.0F) : Mth.clamp((progress - 0.72F) / 0.28F, 0.0F, 1.0F);
        float overshoot = easeOutBack(intro, entry.popStrength());
        float hoverWave = Mth.sin(progress * 8.0F + entry.phase()) * entry.hoverAmplitude() * (1.0F - settle * 0.55F);
        float localSway = Mth.sin(progress * 6.8F + entry.phase()) * entry.swayAmplitude() * (1.0F - settle * 0.35F);
        float tilt = entry.tiltDegrees() * entry.direction() * (1.0F - settle * 0.45F)
                + Mth.sin(progress * 5.5F + entry.phase()) * entry.tiltDegrees() * 0.45F * entry.direction();
        double arcRise = anchored ? easedRise * entry.arcHeight() : Math.sin(progress * Mth.PI) * entry.arcHeight();
        Vec3 anchor = entryAnchor(minecraft, entry, partialTick);
        if (anchor == null) {
            return false;
        }
        Vec3 position = anchor.add(entry.position()).add(entry.drift().scale(easedRise)).add(0.0D, hoverWave + arcRise, 0.0D);
        float alpha = intro * (1.0F - fadeOut);
        if (alpha <= 0.03F) {
            return false;
        }
        int color = ToucanColors.multiplyAlpha(entry.color(), alpha);
        float textWidth = font.width(entry.label());
        float ascentScale = anchored ? Mth.lerp(easedRise, 0.5F, 2.5F) : 1.0F;
        float scale = entry.scale() * ascentScale * Mth.lerp(overshoot, 0.62F, 1.0F) * Mth.lerp(fadeOut, 1.0F, 0.82F);
        float stretch = entry.popStrength() * 0.16F * (1.0F - intro) + entry.popStrength() * 0.08F * Mth.sin(intro * Mth.PI);

        poseStack.pushPose();
        poseStack.translate(position.x - cameraPosition.x, position.y - cameraPosition.y, position.z - cameraPosition.z);
        poseStack.mulPose(minecraft.getEntityRenderDispatcher().cameraOrientation());
        poseStack.translate(localSway, 0.0D, 0.0D);
        poseStack.mulPose(Axis.ZP.rotationDegrees(tilt));
        poseStack.scale(scale * (1.0F + stretch), -scale * (1.0F - stretch * 0.65F), scale);

        Matrix4f matrix = poseStack.last().pose();
        drawOutlinedText(font, bufferSource, matrix, entry.label(), -textWidth / 2.0F, 0.0F, color, entry.italic());
        poseStack.popPose();
        return true;
    }

    private static Vec3 entryAnchor(Minecraft minecraft, Entry entry, float partialTick) {
        if (entry.anchorEntityId() == NO_ANCHOR_ENTITY_ID) {
            return Vec3.ZERO;
        }
        if (minecraft.level == null) {
            return null;
        }
        Entity anchor = minecraft.level.getEntity(entry.anchorEntityId());
        if (anchor == null || !anchor.isAlive()) {
            return null;
        }
        return anchor.getPosition(partialTick).add(0.0D, anchor.getBbHeight() * entry.anchorYOffsetScale(), 0.0D);
    }

    private static float easeOutBack(float progress, float strength) {
        float clamped = Mth.clamp(progress, 0.0F, 1.0F);
        float c1 = 1.70158F * strength;
        float c3 = c1 + 1.0F;
        float t = clamped - 1.0F;
        return 1.0F + c3 * t * t * t + c1 * t * t;
    }

    private record Entry(
            Vec3 position,
            Vec3 drift,
            String label,
            int color,
            boolean italic,
            float phase,
            float direction,
            long bornAtMillis,
            int lifetimeMillis,
            float scale,
            float popStrength,
            float hoverAmplitude,
            float swayAmplitude,
            float tiltDegrees,
            double arcHeight,
            int anchorEntityId,
            double anchorYOffsetScale) {
        private boolean isExpired(long nowMillis) {
            return nowMillis - bornAtMillis > lifetimeMillis;
        }

        private float progress(long nowMillis) {
            return Mth.clamp((nowMillis - bornAtMillis) / (float) lifetimeMillis, 0.0F, 1.0F);
        }
    }
}
