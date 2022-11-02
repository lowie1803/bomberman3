/**
 * @author low_
 */
package com.example.bomberman.entities.bomb;

import com.example.bomberman.Board;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.character.Character;
import com.example.bomberman.graphics.Screen;


public class Flare extends Entity {
    private final Board board;
    private final int direction;
    private final int radius;
    public FlareSegment[] flareSegments = new FlareSegment[1];

    /**
     * Constructor.
     *
     * @param x         x-coordinate
     * @param y         y-coordinate
     * @param direction flare direction
     * @param radius    flare radius
     * @param board     current game board state
     */
    public Flare(int x, int y, int direction, int radius, Board board) {
        coordinateX = x;
        coordinateY = y;
        this.direction = direction;
        this.radius = radius;
        this.board = board;
        createSegments();
    }

    /**
     * create Flare Segment for this bomb flare
     */
    private void createSegments() {
        flareSegments = new FlareSegment[FlareLength()];
        boolean isLast = false;

        int x = (int) coordinateX;
        int y = (int) coordinateY;
        for (int i = 0; i < flareSegments.length; i++) {
            if (i == flareSegments.length - 1) {
                isLast = true;
            }
            if (direction == 0) y--;
            if (direction == 1) x++;
            if (direction == 2) y++;
            if (direction == 3) x--;
            flareSegments[i] = new FlareSegment(x, y, direction, isLast);
        }
    }

    @Override
    public void update() {
    }

    /**
     * Draw Flare Segment on the screen.
     *
     * @param screen screen
     */
    @Override
    public void render(Screen screen) {
        for (FlareSegment tmp : flareSegments) {
            tmp.render(screen);
        }
    }

    /**
     * Check and kill (if possible) on collision with other entity.
     *
     * @param e Other entity.
     * @return false if collide.
     */
    @Override
    public boolean collide(Entity e) {
        if (e instanceof Character) {
            ((Character) e).killAndRemove();
            return false;
        }
        return true;

    }

    /**
     * Calculate length of the flare.
     *
     * @return length
     */
    public int FlareLength() {
        int radius = 0;
        int x = (int) coordinateX;
        int y = (int) coordinateY;
        while (radius < this.radius) {
            if (direction == 0) y--;
            if (direction == 1) x++;
            if (direction == 2) y++;
            if (direction == 3) x--;
            Entity tmp = board.getEntity(x, y, null);
            if (tmp instanceof Character) {
                for (Character cur : board.characters) {
                    if (cur.getXCell() == x && cur.getYCell() == y) {
                        cur.collide(this);
                    }
                }
                tmp = board.getEntityAt(x, y);
                tmp.collide(this);
                radius++;
                continue;
            }
            if (!tmp.collide(this)) {
                break;
            }
            radius++;
        }
        return radius;
    }

    /**
     * get flare segment at some point
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return flareSegment
     */
    public FlareSegment flareSegmentAt(int x, int y) {
        for (FlareSegment flareSegment : flareSegments) {
            if (flareSegment.getX() == x && flareSegment.getY() == y)
                return flareSegment;
        }
        return null;
    }
}
