/**
 * Class Sprite
 *
 * @author low_
 */
package com.example.bomberman.graphics;

import java.util.Arrays;

public class Sprite {
    public final int SIZE = 16;
    private int x, y;
    public int[] pixels;
    private SpriteSheet sheet;

    /**
     * Board sprites
     */
    public static Sprite grass = new Sprite(6, 0, SpriteSheet.tiles);
    public static Sprite brick = new Sprite(7, 0, SpriteSheet.tiles);
    public static Sprite wall = new Sprite(5, 0, SpriteSheet.tiles);
    public static Sprite portal = new Sprite(4, 0, SpriteSheet.tiles);

    /**
     * Bomber
     */
    public static Sprite player_up = new Sprite(0, 0, SpriteSheet.tiles);
    public static Sprite player_down = new Sprite(2, 0, SpriteSheet.tiles);
    public static Sprite player_left = new Sprite(3, 0, SpriteSheet.tiles);
    public static Sprite player_right = new Sprite(1, 0, SpriteSheet.tiles);

    public static Sprite player_up_1 = new Sprite(0, 1, SpriteSheet.tiles);
    public static Sprite player_up_2 = new Sprite(0, 2, SpriteSheet.tiles);

    public static Sprite player_down_1 = new Sprite(2, 1, SpriteSheet.tiles);
    public static Sprite player_down_2 = new Sprite(2, 2, SpriteSheet.tiles);

    public static Sprite player_left_1 = new Sprite(3, 1, SpriteSheet.tiles);
    public static Sprite player_left_2 = new Sprite(3, 2, SpriteSheet.tiles);

    public static Sprite player_right_1 = new Sprite(1, 1, SpriteSheet.tiles);
    public static Sprite player_right_2 = new Sprite(1, 2, SpriteSheet.tiles);

    public static Sprite player_dead1 = new Sprite(4, 2, SpriteSheet.tiles);

    /**
     * Characters
     */
    // BALLOM
    public static Sprite ballom_left1 = new Sprite(9, 0, SpriteSheet.tiles);
    public static Sprite ballom_left2 = new Sprite(9, 1, SpriteSheet.tiles);
    public static Sprite ballom_left3 = new Sprite(9, 2, SpriteSheet.tiles);

    public static Sprite ballom_right1 = new Sprite(10, 0, SpriteSheet.tiles);
    public static Sprite ballom_right2 = new Sprite(10, 1, SpriteSheet.tiles);
    public static Sprite ballom_right3 = new Sprite(10, 2, SpriteSheet.tiles);

    public static Sprite ballom_dead = new Sprite(9, 3, SpriteSheet.tiles);

    //ONEAL
    public static Sprite oneal_left1 = new Sprite(11, 0, SpriteSheet.tiles);
    public static Sprite oneal_left2 = new Sprite(11, 1, SpriteSheet.tiles);
    public static Sprite oneal_left3 = new Sprite(11, 2, SpriteSheet.tiles);

    public static Sprite oneal_right1 = new Sprite(12, 0, SpriteSheet.tiles);
    public static Sprite oneal_right2 = new Sprite(12, 1, SpriteSheet.tiles);
    public static Sprite oneal_right3 = new Sprite(12, 2, SpriteSheet.tiles);

    public static Sprite oneal_up1 = new Sprite(11, 0, SpriteSheet.tiles);
    public static Sprite oneal_up2 = new Sprite(11, 1, SpriteSheet.tiles);
    public static Sprite oneal_up3 = new Sprite(11, 2, SpriteSheet.tiles);

    public static Sprite oneal_down1 = new Sprite(12, 0, SpriteSheet.tiles);
    public static Sprite oneal_down2 = new Sprite(12, 1, SpriteSheet.tiles);
    public static Sprite oneal_down3 = new Sprite(12, 2, SpriteSheet.tiles);

    public static Sprite oneal_dead = new Sprite(11, 3, SpriteSheet.tiles);

    //ALL
    public static Sprite mob_dead1 = new Sprite(15, 0, SpriteSheet.tiles);
    public static Sprite mob_dead2 = new Sprite(15, 1, SpriteSheet.tiles);
    public static Sprite mob_dead3 = new Sprite(15, 2, SpriteSheet.tiles);

    /**
     * Bomb
     */
    public static Sprite bomb = new Sprite(0, 3, SpriteSheet.tiles);
    public static Sprite bomb_1 = new Sprite(1, 3, SpriteSheet.tiles);
    public static Sprite bomb_2 = new Sprite(2, 3, SpriteSheet.tiles);

    /**
     * Flare
     */
    public static Sprite bomb_exploded2 = new Sprite(0, 6, SpriteSheet.tiles);

    public static Sprite explosion_vertical2 = new Sprite(3, 5, SpriteSheet.tiles);

    public static Sprite explosion_horizontal2 = new Sprite(1, 9, SpriteSheet.tiles);

    public static Sprite explosion_horizontal_left_last2 = new Sprite(0, 9, SpriteSheet.tiles);

    public static Sprite explosion_horizontal_right_last2 = new Sprite(2, 9, SpriteSheet.tiles);

    public static Sprite explosion_vertical_top_last2 = new Sprite(3, 4, SpriteSheet.tiles);

    public static Sprite explosion_vertical_down_last2 = new Sprite(3, 6, SpriteSheet.tiles);

    /**
     * Brick explosion.
     */
    public static Sprite brick_exploded = new Sprite(7, 1, SpriteSheet.tiles);
    public static Sprite brick_exploded1 = new Sprite(7, 2, SpriteSheet.tiles);
    public static Sprite brick_exploded2 = new Sprite(7, 3, SpriteSheet.tiles);

    /**
     * Powerup
     */
    public static Sprite powerup_bombs = new Sprite(0, 10, SpriteSheet.tiles);
    public static Sprite powerup_flares = new Sprite(1, 10, SpriteSheet.tiles);
    public static Sprite powerup_speed = new Sprite(2, 10, SpriteSheet.tiles);

    public Sprite(int x, int y, SpriteSheet sheet) {
        pixels = new int[SIZE * SIZE];
        this.x = x * SIZE;
        this.y = y * SIZE;
        this.sheet = sheet;
        load();
    }

    public Sprite(int color) {
        pixels = new int[SIZE * SIZE];
        setColor(color);
    }

    private void setColor(int color) {
        Arrays.fill(pixels, color);
    }

    private void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * this.sheet.WIDTH];
            }
        }
    }

    public static Sprite movingSprite(Sprite normal, Sprite next1, Sprite next2, int animate, int time) {
        int calc = animate % time;
        int diff = time / 3;

        if (calc < diff) {
            return normal;
        }

        if (calc < diff * 2) {
            return next1;
        }

        return next2;
    }

    public static Sprite movingSprite(Sprite normal, Sprite next1, int animate, int time) {
        int diff = time / 2;
        return (animate % time > diff) ? normal : next1;
    }

    public int getSize() {
        return SIZE;
    }

    public int getPixel(int i) {
        return this.pixels[i];
    }
}
