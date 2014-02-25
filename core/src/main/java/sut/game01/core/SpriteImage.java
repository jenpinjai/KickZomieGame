package sut.game01.core;

import playn.core.Image;

/**
 * Represents the data associated with a single sprite.
 */
class SpriteImage {

    private final Image image;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public SpriteImage(final Image image, int x, int y, int width, int height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Image image() {
        return image;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

}
