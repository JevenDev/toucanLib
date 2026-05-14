package com.jvn.toucanlib.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public final class ToucanHudText {
    private ToucanHudText() {
    }

    /**
     * Draws text with a one-pixel four-direction outline.
     */
    public static void drawOutlinedString(GuiGraphics graphics, Font font, String text, int x, int y, int color, int outlineColor) {
        graphics.drawString(font, text, x + 1, y, outlineColor, false);
        graphics.drawString(font, text, x - 1, y, outlineColor, false);
        graphics.drawString(font, text, x, y + 1, outlineColor, false);
        graphics.drawString(font, text, x, y - 1, outlineColor, false);
        graphics.drawString(font, text, x, y, color, false);
    }

    /**
     * Draws a component with a one-pixel four-direction outline.
     */
    public static void drawOutlinedString(GuiGraphics graphics, Font font, Component text, int x, int y, int color, int outlineColor) {
        graphics.drawString(font, text, x + 1, y, outlineColor, false);
        graphics.drawString(font, text, x - 1, y, outlineColor, false);
        graphics.drawString(font, text, x, y + 1, outlineColor, false);
        graphics.drawString(font, text, x, y - 1, outlineColor, false);
        graphics.drawString(font, text, x, y, color, false);
    }

    /**
     * Draws centered text with a one-pixel four-direction outline.
     */
    public static void drawCenteredOutlinedString(GuiGraphics graphics, Font font, String text, int centerX, int y, int color, int outlineColor) {
        drawOutlinedString(graphics, font, text, centerX - font.width(text) / 2, y, color, outlineColor);
    }

    /**
     * Draws a centered component with a one-pixel four-direction outline.
     */
    public static void drawCenteredOutlinedString(GuiGraphics graphics, Font font, Component text, int centerX, int y, int color, int outlineColor) {
        drawOutlinedString(graphics, font, text, centerX - font.width(text) / 2, y, color, outlineColor);
    }

    /**
     * Replaces the alpha channel of an ARGB or RGB color.
     */
    public static int withAlpha(int color, int alpha) {
        return (Mth.clamp(alpha, 0, 255) << 24) | (color & 0x00FFFFFF);
    }

    /**
     * Formats a tick duration as seconds with one decimal place.
     */
    public static String secondsFromTicks(int ticks) {
        return String.format(java.util.Locale.ROOT, "%.1fs", Math.max(0, ticks) / 20.0F);
    }
}
