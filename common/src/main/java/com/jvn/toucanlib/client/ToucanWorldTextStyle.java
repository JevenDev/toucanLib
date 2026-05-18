package com.jvn.toucanlib.client;

/**
 * Visual parameters for an animated in-world text indicator.
 *
 * <p>The values are intentionally small and direct so mods can keep their own
 * gameplay-specific presets while sharing the same rendering behavior.</p>
 *
 * @param label text to render
 * @param color packed ARGB text color
 * @param italic whether the text should render italic
 * @param lifetimeMillis lifetime of the indicator in milliseconds
 * @param scale world render scale
 * @param scatterRadius randomized horizontal spawn radius for floating entries
 * @param driftRadius horizontal drift radius for floating entries
 * @param riseDistance vertical drift over the indicator lifetime
 * @param popStrength overshoot strength during the intro pop
 * @param hoverAmplitude vertical hover wave amplitude
 * @param swayAmplitude local horizontal sway amplitude
 * @param tiltDegrees maximum billboard tilt in degrees
 */
public record ToucanWorldTextStyle(
        String label,
        int color,
        boolean italic,
        int lifetimeMillis,
        float scale,
        double scatterRadius,
        double driftRadius,
        double riseDistance,
        float popStrength,
        float hoverAmplitude,
        float swayAmplitude,
        float tiltDegrees) {
    /**
     * Creates a style and validates the values used by the renderer.
     */
    public ToucanWorldTextStyle {
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("label must not be blank");
        }
        if (lifetimeMillis <= 0) {
            throw new IllegalArgumentException("lifetimeMillis must be positive");
        }
        if (scale <= 0.0F) {
            throw new IllegalArgumentException("scale must be positive");
        }
    }

    /**
     * Creates a compact default style suitable for simple damage numbers.
     *
     * @param label text to render
     * @param color packed ARGB text color
     * @return a default indicator style
     */
    public static ToucanWorldTextStyle simple(String label, int color) {
        return new ToucanWorldTextStyle(label, color, false, 900, 0.025F, 0.16D, 0.12D, 0.28D, 1.55F, 0.04F, 0.05F, 6.0F);
    }
}
