package com.jvn.toucanlib.client;

public final class toucanScreenRects {
    private toucanScreenRects() {
    }

    /**
     * Returns true when a point is inside an integer rectangle.
     */
    public static boolean contains(int pointX, int pointY, int x, int y, int width, int height) {
        return contains((double) pointX, (double) pointY, x, y, width, height);
    }

    /**
     * Returns true when a point is inside an integer rectangle.
     */
    public static boolean contains(double pointX, double pointY, int x, int y, int width, int height) {
        return pointX >= x && pointX < x + width && pointY >= y && pointY < y + height;
    }

    /**
     * Creates a screen rectangle.
     */
    public static toucanScreenRect of(int x, int y, int width, int height) {
        return new toucanScreenRect(x, y, width, height);
    }
}
