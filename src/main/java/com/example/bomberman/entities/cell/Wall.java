/**
 * @author low_
 */
package com.example.bomberman.entities.cell;

import com.example.bomberman.graphics.Sprite;

public class Wall extends Cell {

    /**
     * Constructor.
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param sprite Sprite.
     */
    public Wall(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

}
