/**
 * @author low_
 */
package com.example.bomberman.entities.cell.destroyable;

import com.example.bomberman.LoadMap.Coordinates;
import com.example.bomberman.graphics.Screen;
import com.example.bomberman.graphics.Sprite;

public class Brick extends DestroyableCell {
    public Brick(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(Screen screen) {
        int x = Coordinates.cellToPixel(coordinateX);
        int y = Coordinates.cellToPixel(coordinateY);

        if (destroyed) {
            sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
            screen.renderEntityWithSpriteBelow(x, y, this, spriteBelow);
        } else {
            screen.renderEntity(x, y, this);
        }
    }
}
