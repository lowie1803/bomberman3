/**
 * @author low_
 */
package com.example.bomberman.entities.cell;

import com.example.bomberman.LoadMap.Coordinates;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.graphics.Screen;
import com.example.bomberman.graphics.Sprite;

/**
 * Non-animated entities.
 */
public abstract class Cell extends Entity {
    /**
     * Constructor.
     *
     * @param x      x-coordinate
     * @param y      y-coordinate
     * @param sprite sprite
     */
    public Cell(int x, int y, Sprite sprite) {
        coordinateX = x;
        coordinateY = y;
        this.sprite = sprite;
    }

    /**
     * By default, no entity should pass.
     *
     * @param e other entity
     * @return false
     */
    @Override
    public boolean collide(Entity e) {
        return false;
    }

    /**
     * Render this cell to screen
     *
     * @param screen screen
     */
    @Override
    public void render(Screen screen) {
        screen.renderEntity(
                Coordinates.cellToPixel(coordinateX),
                Coordinates.cellToPixel(coordinateY),
                this
        );
    }

    @Override
    public void update() {
    }
}
