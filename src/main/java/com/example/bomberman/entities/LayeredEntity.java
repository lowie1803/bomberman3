/**
 * @author low_
 */
package com.example.bomberman.entities;

import com.example.bomberman.entities.cell.destroyable.DestroyableCell;
import com.example.bomberman.graphics.Screen;

import java.util.LinkedList;

public class LayeredEntity extends Entity {
    protected LinkedList<Entity> entities = new LinkedList<>();

    public LayeredEntity(int x, int y, Entity... entities) {
        coordinateX = x;
        coordinateY = y;
        for (int i = 0; i < entities.length; i++) {
            this.entities.add(entities[i]);
            if (i > 1) {
                if (entities[i] instanceof DestroyableCell)
                    ((DestroyableCell) entities[i]).addBelow(entities[i - 1].getSprite());
            }
        }
    }

    @Override
    public void update() {
        clearRemoved();
        getTopEntity().update();
    }

    @Override
    public void render(Screen screen) {
        getTopEntity().render(screen);
    }

    public Entity getTopEntity() {
        return entities.getLast();
    }

    private void clearRemoved() {
        Entity top = getTopEntity();
        if (top.isRemoved()) {
            entities.removeLast();
        }
    }

    @Override
    public boolean collide(Entity e) {
        return getTopEntity().collide(e);
    }
}
