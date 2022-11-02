/**
 * @author low_
 */
package com.example.bomberman.entities.cell.destroyable;

import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.bomb.Flare;
import com.example.bomberman.entities.cell.Cell;
import com.example.bomberman.graphics.Sprite;

/**
 * Static cell that can be destroyed.
 */
public class DestroyableCell extends Cell {
    private int animate = 0;
    protected boolean destroyed = false;
    protected int timeRemain = 20;
    protected Sprite spriteBelow = Sprite.grass;

    public DestroyableCell(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        if (destroyed) {
            int MAX_ANIMATE = 60;
            if (animate < MAX_ANIMATE)
                animate++;
            else
                animate = 0;
            if (timeRemain > 0)
                timeRemain--;
            else
                remove();
        }
    }

    public void destroy() {
        destroyed = true;
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Flare)
            destroy();
        return false;
    }

    public void addBelow(Sprite sprite) {
        spriteBelow = sprite;
    }

    protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
        int animateNum = animate % 30;
        if (animateNum < 10) {
            return normal;
        }
        if (animateNum < 20) {
            return x1;
        }
        return x2;
    }
}
