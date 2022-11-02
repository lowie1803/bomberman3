/**
 * @author low_
 */

package com.example.bomberman.entities.character;

import com.example.bomberman.Board;
import com.example.bomberman.Game;
import com.example.bomberman.entities.AnimatedEntity;
import com.example.bomberman.graphics.Screen;

/**
 * Abstract class to represent movable characters.
 */
public abstract class Character extends AnimatedEntity {
    protected Board board;
    protected int direction = -1;
    protected boolean alive = true;
    protected boolean moving = false;
    public int timeAfter = 40;

    /**
     * constructor.
     *
     * @param x     coordinate x
     * @param y     coordinate y
     * @param board board
     */
    public Character(int x, int y, Board board) {
        coordinateX = x;
        coordinateY = y;
        this.board = board;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(Screen screen);

    /**
     * check collision.
     *
     * @param c character
     * @return boolean true or false
     */
    public boolean collisionWith(Character c) {
        return coordinateX + 13 > c.getX() && coordinateX < c.getX() + 13 && coordinateY + 13 > c.getY() && coordinateY < c.getY() + 13;
    }

    /**
     * Tính toán hướng đi.
     */
    protected abstract void calculateMove();

    protected abstract void move(double xa, double ya);

    /**
     * Được gọi khi đối tượng bị tiêu diệt.
     */
    public abstract void killAndRemove();

    /**
     * Xử lý hiệu ứng bị tiêu diệt.
     */
    protected abstract void afterRemoval();

    /**
     * Kiểm tra xem đối tượng có di chuyển tới vị trí đã tính toán hay không.
     *
     * @param x double x
     * @param y double y
     * @return boolean true or false
     */
    protected abstract boolean movable(double x, double y);

    protected double getXMessage() {
        return (coordinateX * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
    }

    protected double getYMessage() {
        return (coordinateY * Game.SCALE) - (sprite.SIZE / 2 * Game.SCALE);
    }
}