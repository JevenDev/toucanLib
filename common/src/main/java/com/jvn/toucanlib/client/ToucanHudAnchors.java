package com.jvn.toucanlib.client;

/**
 * Resolves {@link ToucanHudAnchor} values into screen coordinates.
 */
public final class ToucanHudAnchors {
    private ToucanHudAnchors() {
    }

    /**
     * Resolves the left X coordinate for anchored content.
     */
    public static int x(ToucanHudAnchor anchor, int screenWidth, int contentWidth, int offsetX) {
        return switch (anchor) {
            case TOP_LEFT, BOTTOM_LEFT -> offsetX;
            case TOP_RIGHT, BOTTOM_RIGHT -> screenWidth - contentWidth - offsetX;
            case CENTER -> (screenWidth - contentWidth) / 2 + offsetX;
        };
    }

    /**
     * Resolves the top Y coordinate for anchored content.
     */
    public static int y(ToucanHudAnchor anchor, int screenHeight, int contentHeight, int offsetY) {
        return switch (anchor) {
            case TOP_LEFT, TOP_RIGHT -> offsetY;
            case BOTTOM_LEFT, BOTTOM_RIGHT -> screenHeight - contentHeight - offsetY;
            case CENTER -> (screenHeight - contentHeight) / 2 + offsetY;
        };
    }

    /**
     * Resolves an anchored rectangle for content of the given size.
     */
    public static ToucanScreenRect rect(
            ToucanHudAnchor anchor,
            int screenWidth,
            int screenHeight,
            int contentWidth,
            int contentHeight,
            int offsetX,
            int offsetY
    ) {
        return new ToucanScreenRect(
                x(anchor, screenWidth, contentWidth, offsetX),
                y(anchor, screenHeight, contentHeight, offsetY),
                contentWidth,
                contentHeight
        );
    }
}
