/**
 * Class Sprite
 *
 * @author low_
 */
package com.example.bomberman.graphics;

import java.util.Arrays;

public class Sprite {
    public final int SIZE;
    private int x, y;
    public int[] pixels;
    protected int realWidth;
    protected int realHeight;
    private SpriteSheet sheet;

    /*
    |--------------------------------------------------------------------------
    | Board sprites
    |--------------------------------------------------------------------------
     */
    public static Sprite grass = new Sprite(16, 6, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite brick = new Sprite(16, 7, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite wall = new Sprite(16, 5, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite portal = new Sprite(16, 4, 0, SpriteSheet.tiles, 14, 14);

    /*
    |--------------------------------------------------------------------------
    | Bomber Sprites
    |--------------------------------------------------------------------------
     */
    public static Sprite player_up = new Sprite(16, 0, 0, SpriteSheet.tiles, 12, 16);
    public static Sprite player_down = new Sprite(16, 2, 0, SpriteSheet.tiles, 12, 15);
    public static Sprite player_left = new Sprite(16, 3, 0, SpriteSheet.tiles, 10, 15);
    public static Sprite player_right = new Sprite(16, 1, 0, SpriteSheet.tiles, 10, 16);

    public static Sprite player_up_1 = new Sprite(16, 0, 1, SpriteSheet.tiles, 12, 16);
    public static Sprite player_up_2 = new Sprite(16, 0, 2, SpriteSheet.tiles, 12, 15);

    public static Sprite player_down_1 = new Sprite(16, 2, 1, SpriteSheet.tiles, 12, 15);
    public static Sprite player_down_2 = new Sprite(16, 2, 2, SpriteSheet.tiles, 12, 16);

    public static Sprite player_left_1 = new Sprite(16, 3, 1, SpriteSheet.tiles, 11, 16);
    public static Sprite player_left_2 = new Sprite(16, 3, 2, SpriteSheet.tiles, 12, 16);

    public static Sprite player_right_1 = new Sprite(16, 1, 1, SpriteSheet.tiles, 11, 16);
    public static Sprite player_right_2 = new Sprite(16, 1, 2, SpriteSheet.tiles, 12, 16);

    public static Sprite player_dead1 = new Sprite(16, 4, 2, SpriteSheet.tiles, 30, 30);

    /*
    |--------------------------------------------------------------------------
    | Character
    |--------------------------------------------------------------------------
     */
    //BALLOM
    public static Sprite balloon_left1 = new Sprite(16, 9, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite balloon_left2 = new Sprite(16, 9, 1, SpriteSheet.tiles, 16, 16);
    public static Sprite balloon_left3 = new Sprite(16, 9, 2, SpriteSheet.tiles, 16, 16);

    public static Sprite balloon_right1 = new Sprite(16, 10, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite balloon_right2 = new Sprite(16, 10, 1, SpriteSheet.tiles, 16, 16);
    public static Sprite balloon_right3 = new Sprite(16, 10, 2, SpriteSheet.tiles, 16, 16);

    public static Sprite balloon_dead = new Sprite(16, 9, 3, SpriteSheet.tiles, 16, 16);

    //ONEAL
    public static Sprite oneal_left1 = new Sprite(16, 11, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite oneal_left2 = new Sprite(16, 11, 1, SpriteSheet.tiles, 16, 16);
    public static Sprite oneal_left3 = new Sprite(16, 11, 2, SpriteSheet.tiles, 16, 16);

    public static Sprite oneal_right1 = new Sprite(16, 12, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite oneal_right2 = new Sprite(16, 12, 1, SpriteSheet.tiles, 16, 16);
    public static Sprite oneal_right3 = new Sprite(16, 12, 2, SpriteSheet.tiles, 16, 16);

    public static Sprite oneal_up1 = new Sprite(16, 11, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite oneal_up2 = new Sprite(16, 11, 1, SpriteSheet.tiles, 16, 16);
    public static Sprite oneal_up3 = new Sprite(16, 11, 2, SpriteSheet.tiles, 16, 16);

    public static Sprite oneal_down1 = new Sprite(16, 12, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite oneal_down2 = new Sprite(16, 12, 1, SpriteSheet.tiles, 16, 16);
    public static Sprite oneal_down3 = new Sprite(16, 12, 2, SpriteSheet.tiles, 16, 16);

    public static Sprite oneal_dead = new Sprite(16, 11, 3, SpriteSheet.tiles, 16, 16);

    //ALL
    public static Sprite mob_dead1 = new Sprite(16, 15, 0, SpriteSheet.tiles, 16, 16);
    public static Sprite mob_dead2 = new Sprite(16, 15, 1, SpriteSheet.tiles, 16, 16);
    public static Sprite mob_dead3 = new Sprite(16, 15, 2, SpriteSheet.tiles, 16, 16);

    /*
    |--------------------------------------------------------------------------
    | Bomb Sprites
    |--------------------------------------------------------------------------
     */
    public static Sprite bomb = new Sprite(16, 0, 3, SpriteSheet.tiles, 15, 15);
    public static Sprite bomb_1 = new Sprite(16, 1, 3, SpriteSheet.tiles, 13, 15);
    public static Sprite bomb_2 = new Sprite(16, 2, 3, SpriteSheet.tiles, 12, 14);

    /*
    |--------------------------------------------------------------------------
    | FlareSegment Sprites
    |--------------------------------------------------------------------------
     */
    public static Sprite bomb_exploded2 = new Sprite(16, 0, 6, SpriteSheet.tiles, 16, 16);

    public static Sprite explosion_vertical2 = new Sprite(16, 3, 5, SpriteSheet.tiles, 16, 16);

    public static Sprite explosion_horizontal2 = new Sprite(16, 1, 9, SpriteSheet.tiles, 16, 16);

    public static Sprite explosion_horizontal_left_last2 = new Sprite(16, 0, 9, SpriteSheet.tiles, 16, 16);

    public static Sprite explosion_horizontal_right_last2 = new Sprite(16, 2, 9, SpriteSheet.tiles, 16, 16);

    public static Sprite explosion_vertical_top_last2 = new Sprite(16, 3, 4, SpriteSheet.tiles, 16, 16);

    public static Sprite explosion_vertical_down_last2 = new Sprite(16, 3, 6, SpriteSheet.tiles, 16, 16);

    /*
    |--------------------------------------------------------------------------
    | Brick FlareSegment
    |--------------------------------------------------------------------------
     */
    public static Sprite brick_exploded = new Sprite(16, 7, 1, SpriteSheet.tiles, 16, 16);
    public static Sprite brick_exploded1 = new Sprite(16, 7, 2, SpriteSheet.tiles, 16, 16);
    public static Sprite brick_exploded2 = new Sprite(16, 7, 3, SpriteSheet.tiles, 16, 16);

    /*
    |--------------------------------------------------------------------------
    | Powerups
    |--------------------------------------------------------------------------
     */
    public static Sprite powerup_bombs = new Sprite(16, 0, 10, SpriteSheet.tiles, 16, 16);
    public static Sprite powerup_flares = new Sprite(16, 1, 10, SpriteSheet.tiles, 16, 16);
    public static Sprite powerup_speed = new Sprite(16, 2, 10, SpriteSheet.tiles, 16, 16);

    public Sprite(int size, int x, int y, SpriteSheet sheet, int rw, int rh) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * SIZE;
        this.y = y * SIZE;
        this.sheet = sheet;
        realWidth = rw;
        realHeight = rh;
        load();
    }

    public Sprite(int size, int color) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        setColor(color);
    }

    private void setColor(int color) {
        Arrays.fill(pixels, color);
    }

    private void load() {
        for (int y = 0; y < SIZE; y++) {
			System.arraycopy(sheet.pixels, (x + this.x) + (y + this.y) * this.sheet.WIDTH, pixels, 0 + y * SIZE, SIZE);
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
