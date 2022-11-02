/**
 * @author low_
 */

package com.example.bomberman.entities.character.enemy;

import com.example.bomberman.Board;
import com.example.bomberman.Game;
import com.example.bomberman.entities.character.enemy.bot_modules.BotEasy;
import com.example.bomberman.graphics.Sprite;

public class Balloon extends Enemy {
    /**
     * constructor.
     *
     * @param x     int x
     * @param y     int y
     * @param board board
     */
    public Balloon(int x, int y, Board board) {
        super(x, y, board, Sprite.balloon_dead, Game.getBomberSpeed() / 2, 100);
        sprite = Sprite.balloon_left1;
        bot = new BotEasy();
    }

    /**
     * override function to choose sprite.
     */
    @Override
    protected void chooseSprite() {
        switch (direction) {
            case 0:
                sprite = Sprite.movingSprite(Sprite.balloon_right1, Sprite.balloon_right2, Sprite.balloon_right3, animate, 60);
                break;
            case 1:
                sprite = Sprite.movingSprite(Sprite.balloon_right1, Sprite.balloon_right2, Sprite.balloon_right3, animate, 60);
                break;
            case 2:
                sprite = Sprite.movingSprite(Sprite.balloon_left1, Sprite.balloon_left2, Sprite.balloon_left3, animate, 60);
                break;
            case 3:
                sprite = Sprite.movingSprite(Sprite.balloon_left1, Sprite.balloon_left2, Sprite.balloon_left3, animate, 60);
                break;
        }
    }
}