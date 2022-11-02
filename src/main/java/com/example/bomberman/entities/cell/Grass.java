/**
 * @author low_
 */
package com.example.bomberman.entities.cell;

import com.example.bomberman.entities.Entity;
import com.example.bomberman.graphics.Sprite;

public class Grass extends Cell {
    /**
     * Constructor.
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param sprite sprite
     */
    public Grass(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    /**
     * Let any entity step on grass.
     *
     * @param e entity
     * @return true always
     */
    @Override
    public boolean collide(Entity e) {
        return true;
    }
}
