package com.jvn.toucanlib.client;

/**
 * Immutable integer rectangle for screen-space layout and hit testing.
 */
public record ToucanScreenRect(int x, int y, int width, int height) {
    public ToucanScreenRect {
        if (width < 0) {
            throw new IllegalArgumentException("width must not be negative");
        }
        if (height < 0) {
            throw new IllegalArgumentException("height must not be negative");
        }
    }

    /**
     * Returns the exclusive right edge.
     */
    public int right() {
        return x + width;
    }

    /**
     * Returns the exclusive bottom edge.
     */
    public int bottom() {
        return y + height;
    }

    /**
     * Returns the horizontal center.
     */
    public int centerX() {
        return x + width / 2;
    }

    /**
     * Returns the vertical center.
     */
    public int centerY() {
        return y + height / 2;
    }

    /**
     * Returns true when a point is inside this rectangle.
     */
    public boolean contains(double pointX, double pointY) {
        return ToucanScreenRects.contains(pointX, pointY, x, y, width, height);
    }
}
