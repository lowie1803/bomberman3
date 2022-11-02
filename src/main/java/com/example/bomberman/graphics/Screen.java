package com.example.bomberman.graphics;
/**
 * Class Screen
 *
 * @author low_
 */

import com.example.bomberman.Board;
import com.example.bomberman.Game;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.character.Bomber;

import java.awt.*;
import java.util.Arrays;

/**
 * Xử lý render cho tất cả Entity và một số màn hình phụ ra Game Panel
 */
public class Screen {
    public static final String FONT_LINK = "res/gamefont.ttf";
    public static final String FONT_NAME = "Falstin";
    public static final Font MAIN_FONT = new Font(FONT_NAME, Font.BOLD, 20 * Game.SCALE);
    public static final Font MAIN_FONT_SMALL = new Font(FONT_NAME, Font.BOLD, 14 * Game.SCALE);
    public static final Font MAIN_FONT_LARGE = new Font(FONT_NAME, Font.BOLD, 30 * Game.SCALE);
    public static final String VICTORY_TEXT = "WIN! :D";
    public static final String DEFEAT_TEXT = "LOSE! :(";

    protected int width, height;
    public int[] pixels;
    private final int transparentColor = 0xffff00ff;

    public static int xOffset = 0, yOffset = 0;

    /**
     * Constructor.
     * @param width width
     * @param height height
     */
    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    /**
     * clear Screen.
     */
    public void clear() {
        Arrays.fill(pixels, 0);
    }

    /**
     * render Entity.
     * @param xp xp
     * @param yp yp
     * @param entity entity
     */
    public void renderEntity(int xp, int yp, Entity entity) {
        xp -= xOffset;
        yp -= yOffset;
        if (entity.getSprite() == null) {
            return;
        }
        for (int y = 0; y < entity.getSprite().getSize(); y++) {
            int ya = y + yp;
            for (int x = 0; x < entity.getSprite().getSize(); x++) {
                int xa = x + xp;
                if (xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height) break;
                if (xa < 0) xa = 0;
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
                if (color != transparentColor) pixels[xa + ya * width] = color;
            }
        }
    }

    /**
     * render Entity With Sprite Below.
     * @param xp tọa độ x
     * @param yp tọa độ y
     * @param entity Entity
     * @param below Entity below
     */
    public void renderEntityWithSpriteBelow(int xp, int yp, Entity entity, Sprite below) {
        xp -= xOffset;
        yp -= yOffset;
        for (int y = 0; y < entity.getSprite().getSize(); y++) {
            int ya = y + yp;
            for (int x = 0; x < entity.getSprite().getSize(); x++) {
                int xa = x + xp;
                if (xa < -entity.getSprite().getSize() || xa >= width || ya < 0 || ya >= height)
                    break; //fix black margins
                if (xa < 0) xa = 0;
                int color = entity.getSprite().getPixel(x + y * entity.getSprite().getSize());
                if (color != transparentColor)
                    pixels[xa + ya * width] = color;
                else
                    pixels[xa + ya * width] = below.getPixel(x + y * below.getSize());
            }
        }
    }

    /**
     * set Left Upper.
     * @param xO Left
     * @param yO Upper
     */
    public static void setOffset(int xO, int yO) {
        xOffset = xO;
        yOffset = yO;
    }

    /**
     * calculate X Offset.
     * @param board board
     * @param bomber bomber
     * @return X offset
     */
    public static int calculateXOffset(Board board, Bomber bomber) {
        if (bomber == null) return 0;
        int temp = xOffset;

        double BomberX = bomber.getX() / 16;
        double complement = 0.5;
        int firstBreakpoint = board.getWidth() / 4;
        int lastBreakpoint = board.getWidth() - firstBreakpoint;

        if (BomberX > firstBreakpoint + complement && BomberX < lastBreakpoint - complement) {
            temp = (int) bomber.getX() - (Game.WIDTH / 2);
        }

        return temp;
    }

    public void drawDefeat(Graphics g, int points) {
        drawGameResult(g, points, DEFEAT_TEXT);
    }

    public void drawVictory(Graphics g, int points) {
        drawGameResult(g, points, VICTORY_TEXT);
    }

    private void drawGameResult(Graphics g, int points, String text) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());
        g.setFont(MAIN_FONT_LARGE);
        g.setColor(Color.white);
        drawCenteredString(text, getRealWidth(), getRealHeight(), -(Game.CELLS_SIZE * 2), g);

        g.setFont(MAIN_FONT_SMALL);
        g.setColor(Color.yellow);
        drawCenteredString("POINTS: " + points, getRealWidth(), getRealHeight() + (Game.CELLS_SIZE * 2) * Game.SCALE, 0, g);
        g.setFont(MAIN_FONT);
        g.setColor(Color.white);
        drawCenteredString("[Press space to replay]", getRealWidth(), getRealHeight() + (Game.CELLS_SIZE * 4) * Game.SCALE, 0, g);
    }

    /**
     * Draw Change Level Screen.
     * @param g Graphic g
     * @param level level
     */
    public void drawChangeLevel(Graphics g, int level) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getRealWidth(), getRealHeight());

        g.setFont(MAIN_FONT_LARGE);
        g.setColor(Color.white);
        drawCenteredString("LEVEL " + level, getRealWidth(), getRealHeight(), 0, g);

    }

    /**
     * Draw Game Paused Screen.
     * @param g Graphic
     */
    public void drawPaused(Graphics g) {
        g.setFont(MAIN_FONT_LARGE);
        g.setColor(Color.white);
        drawCenteredString("PAUSED", getRealWidth(), getRealHeight(), 0, g);
    }

    /**
     * Draw CenterString.
     * @param s text
     * @param w width
     * @param h height
     * @param padding padding
     * @param g graphic
     */
    public void drawCenteredString(String s, int w, int h, int padding, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2) + padding;
        g.drawString(s, x, y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getRealWidth() {
        return width * Game.SCALE;
    }

    public int getRealHeight() {
        return height * Game.SCALE;
    }
}
