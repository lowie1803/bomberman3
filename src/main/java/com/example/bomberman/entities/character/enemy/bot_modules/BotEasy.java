/**
 * @author low_
 */

package com.example.bomberman.entities.character.enemy.bot_modules;

public class BotEasy extends Bot {
    /**
     * Random next move.
     *
     * @return direction
     */
    @Override
    public int nextDirection() {
        return random.nextInt(4);
    }
}