/**
 * @author low_
 */

package com.example.bomberman.entities.character.enemy;

import com.example.bomberman.Board;
import com.example.bomberman.Game;
import com.example.bomberman.entities.character.enemy.bot_modules.BotEasy;
import com.example.bomberman.graphics.Sprite;

public class Ballom extends Enemy {
    /**
     * constructor.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param board game board
     */
    public Ballom(int x, int y, Board board) {
        super(x, y, board, Sprite.ballom_dead, Game.getBomberSpeed() / 2, 100);
        sprite = Sprite.ballom_left1;
        bot = new BotEasy();
    }

    /**
     * override function to choose sprite.
     */
    @Override
    protected void chooseSprite() {
        switch (direction) {
            case 0:
                sprite = Sprite.movingSprite(
                        Sprite.ballom_right1,
                        Sprite.ballom_right2,
                        Sprite.ballom_right3,
                        animate,
                        60
                );
                break;
            case 1:
                sprite = Sprite.movingSprite(
                        Sprite.ballom_right1,
                        Sprite.ballom_right2,
                        Sprite.ballom_right3,
                        animate,
                        60
                );
                break;
            case 2:
                sprite = Sprite.movingSprite(
                        Sprite.ballom_left1,
                        Sprite.ballom_left2,
                        Sprite.ballom_left3,
                        animate,
                        60
                );
                break;
            case 3:
                sprite = Sprite.movingSprite(
                        Sprite.ballom_left1,
                        Sprite.ballom_left2,
                        Sprite.ballom_left3,
                        animate,
                        60
                );
                break;
        }
    }
}