/**
 * @author low_
 */
package com.example.bomberman.entities.bomb;

import com.example.bomberman.Game;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.character.Character;
import com.example.bomberman.graphics.Screen;
import com.example.bomberman.graphics.Sprite;

public class FlareSegment extends Entity {
    /**
     * @param direction flare direction
     * @param isEnding  check if it's flare endings, to render differently
     */
    public FlareSegment(int x, int y, int direction, boolean isEnding) {
        coordinateX = x;
        coordinateY = y;

        if (direction == 0) {
            if (isEnding) {
                sprite = Sprite.explosion_vertical_top_last2;
            } else {
                sprite = Sprite.explosion_vertical2;
            }
        }
        if (direction == 1) {
            if (isEnding) {
                sprite = Sprite.explosion_horizontal_right_last2;
            } else {
                sprite = Sprite.explosion_horizontal2;
            }
        }
        if (direction == 2) {
            if (isEnding) {
                sprite = Sprite.explosion_vertical_down_last2;
            } else {
                sprite = Sprite.explosion_vertical2;
            }
        }
        if (direction == 3) {
            if (isEnding) {
                sprite = Sprite.explosion_horizontal_left_last2;
            } else {
                sprite = Sprite.explosion_horizontal2;
            }
        }
    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Character) {
            ((Character) e).killAndRemove();
        }
        return true;
    }

    @Override
    public void render(Screen screen) {
        screen.renderEntity(
                (int) coordinateX * Game.CELLS_SIZE,
                (int) coordinateY * Game.CELLS_SIZE,
                this
        );
    }
}