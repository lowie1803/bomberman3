/**
 * @author low_
 */

package com.example.bomberman.entities.cell.item;

import com.example.bomberman.Board;
import com.example.bomberman.Game;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.character.Bomber;
import com.example.bomberman.graphics.Sprite;

public class FlareItem extends Item {

    public FlareItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public boolean collide(Entity e) {
        if (isRemoved()) return false;

        if (e instanceof Bomber) {
            Game.addBombRadius(1);
            remove();
            Board.getItemSound.play();
            return true;
        }
        return false;
    }

}
