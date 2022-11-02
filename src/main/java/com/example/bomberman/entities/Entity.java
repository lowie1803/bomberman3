/**
 * @author low_
 */
package com.example.bomberman.entities;

import com.example.bomberman.LoadMap.Coordinates;
import com.example.bomberman.graphics.IRender;
import com.example.bomberman.graphics.Screen;
import com.example.bomberman.graphics.Sprite;

public abstract class Entity implements IRender {
    protected double coordinateX, coordinateY;
    protected boolean isRemoved = false;
    protected Sprite sprite;

    @Override
    public abstract void update();

    @Override
    public abstract void render(Screen screen);

    public void remove() {
        isRemoved = true;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public abstract boolean collide(Entity e);

    public double getX() {
        return coordinateX;
    }

    public double getY() {
        return coordinateY;
    }


    public int getXCell() {
        return Coordinates.pixelToCell(coordinateX + sprite.SIZE / 2);
    }

    public int getYCell() {
        return Coordinates.pixelToCell(coordinateY - sprite.SIZE / 2);
    }
}
