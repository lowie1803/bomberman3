/**
 * @author low_
 */
package com.example.bomberman.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class SpriteSheet {

    private final String path;
    public final int WIDTH;
    public final int HEIGHT;
    public int[] pixels;

    public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256, 256);


    public SpriteSheet(String path, int sizeW, int sizeH) {
        this.path = path;
        WIDTH = sizeW;
        HEIGHT = sizeH;
        this.pixels = new int[WIDTH * WIDTH];
        load();
    }

    private void load() {
        try {
            URL a = SpriteSheet.class.getResource(this.path);
            assert a != null;
            BufferedImage image = ImageIO.read(a);
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, this.pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
