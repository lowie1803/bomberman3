/**
 * @author low_
 */
package com.example.bomberman.entities.cell;

import com.example.bomberman.Board;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.character.Bomber;
import com.example.bomberman.graphics.Sprite;

public class Portal extends Cell {
    protected Board board;

    /**
     * Constructor.
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param sprite sprite
     * @param board  gamemap
     */
    public Portal(int x, int y, Sprite sprite, Board board) {
        super(x, y, sprite);
        this.board = board;
    }

    /**
     * Handle collision with other entities.
     *
     * @param e entity to be checked
     * @return true if no collision occurs.
     */
    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            if (!board.detectNoEnemies())
                return false;

            if (e.getXCell() == getX() && e.getYCell() == getY()) {
                if (board.detectNoEnemies())
                    board.nextLevel();
            }

            return true;
        }
        return false;
    }
}
