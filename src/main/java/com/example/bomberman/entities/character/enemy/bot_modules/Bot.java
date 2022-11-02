/**
 * @author low_
 */

package com.example.bomberman.entities.character.enemy.bot_modules;

import java.util.Random;

public abstract class Bot {
    protected Random random = new Random();

    /**
     * Instruct the sprite to know how to move.
     *
     * @return direction for AI.
     */
    public abstract int nextDirection();
}
