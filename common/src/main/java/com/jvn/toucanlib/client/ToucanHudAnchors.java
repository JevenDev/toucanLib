package com.jvn.toucanlib.client;

public final class toucanHudAnchors {
    private toucanHudAnchors() {
    }

    /**
     * Resolves the left X coordinate for anchored content.
     */
    public static int x(toucanHudAnchor anchor, int screenWidth, int contentWidth, int offsetX) {
        return switch (anchor) {
            case TOP_LEFT, BOTTOM_LEFT -> offsetX;
            case TOP_RIGHT, BOTTOM_RIGHT -> screenWidth - contentWidth - offsetX;
            case CENTER -> (screenWidth - contentWidth) / 2 + offsetX;
        };
    }

    /**
     * Resolves the top Y coordinate for anchored content.
     */
    public static int y(toucanHudAnchor anchor, int screenHeight, int contentHeight, int offsetY) {
        return switch (anchor) {
            case TOP_LEFT, TOP_RIGHT -> offsetY;
            case BOTTOM_LEFT, BOTTOM_RIGHT -> screenHeight - contentHeight - offsetY;
            case CENTER -> (screenHeight - contentHeight) / 2 + offsetY;
        };
    }

    /**
     * Resolves an anchored rectangle for content of the given size.
     */
    public static toucanScreenRect rect(
            toucanHudAnchor anchor,
            int screenWidth,
            int screenHeight,
            int contentWidth,
            int contentHeight,
            int offsetX,
            int offsetY
    ) {
        return new toucanScreenRect(
                x(anchor, screenWidth, contentWidth, offsetX),
                y(anchor, screenHeight, contentHeight, offsetY),
                contentWidth,
                contentHeight
        );
    }
}
