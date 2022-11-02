/**
 * @author low_
 */

package com.example.bomberman.entities.character.enemy;

import com.example.bomberman.Board;
import com.example.bomberman.Game;
import com.example.bomberman.LoadMap.Coordinates;
import com.example.bomberman.SoundPlayer;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.Message;
import com.example.bomberman.entities.bomb.Flare;
import com.example.bomberman.entities.bomb.FlareSegment;
import com.example.bomberman.entities.character.Bomber;
import com.example.bomberman.entities.character.Character;
import com.example.bomberman.entities.character.enemy.bot_modules.Bot;
import com.example.bomberman.graphics.Screen;
import com.example.bomberman.graphics.Sprite;

import java.awt.*;
import java.io.File;

public abstract class Enemy extends Character {
    protected int points;
    protected double speed;
    protected Bot bot;
    protected final double MAX_STEPS;
    protected final double rest;
    protected double steps;
    protected int finalAnimation = 30;
    protected Sprite deadSprite;
    public static SoundPlayer enemyDieSound = new SoundPlayer(new File("res/sound/enemyDie.wav"));

    /**
     * constructor.
     *
     * @param x      int x
     * @param y      int y
     * @param board  board
     * @param dead   sprite
     * @param speed  double speed
     * @param points int points
     */
    public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
        super(x, y, board);
        this.points = points;
        this.speed = speed;
        MAX_STEPS = Game.CELLS_SIZE / speed;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        steps = 0;
        timeAfter = 20;
        deadSprite = dead;
    }

    /**
     * override function to upd.
     */
    @Override
    public void update() {
        animate();
        if (!alive) {
            afterRemoval();

            return;
        }
        calculateMove();
    }

    /**
     * override function to render.
     *
     * @param screen
     */
    @Override
    public void render(Screen screen) {
        if (alive) {
            chooseSprite();
        } else {
            if (timeAfter > 0) {
                sprite = deadSprite;
                animate = 0;
            } else {
                sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
            }
        }
        screen.renderEntity((int) coordinateX, (int) coordinateY - sprite.SIZE, this);
    }

    /**
     * override function to calculate direction moving.
     */
    @Override
    public void calculateMove() {
        int x1 = 0;
        int y1 = 0;

        if (steps <= 0) {
            direction = bot.nextDirection();
            steps = MAX_STEPS;
        }

        if (direction == 0) {
            y1--;
        }
        if (direction == 1) {
            x1++;
        }
        if (direction == 2) {
            y1++;
        }
        if (direction == 3) {
            x1--;
        }
        if (movable(x1, y1)) {
            steps += -rest - 1;
            move(speed * x1, speed * y1);
            moving = true;
        } else {
            moving = false;
            steps = 0;
        }
    }

    @Override
    public void move(double u, double v) {
        if (!alive) {
            return;
        }
        coordinateX += u;
        coordinateY += v;
    }

    /**
     * override function to check if character can move to position x,y.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if collided
     */
    @Override
    public boolean movable(double x, double y) {
        int u = (int) x;
        int v = (int) y;
        if (direction == 0) {
            u += Coordinates.pixelToCell(sprite.getSize() / 2 + coordinateX);
            v += Coordinates.pixelToCell(-17 + sprite.getSize() + coordinateY);
        } else if (direction == 1) {
            u += Coordinates.pixelToCell(1 + coordinateX);
            v += Coordinates.pixelToCell(-16 + sprite.getSize() / 2 + coordinateY);
        } else if (direction == 2) {
            u += Coordinates.pixelToCell(sprite.getSize() / 2 + coordinateX);
            v += Coordinates.pixelToCell(-15 + coordinateY);
        } else if (direction == 3) {
            u += Coordinates.pixelToCell(-1 + sprite.getSize() + coordinateX);
            v += Coordinates.pixelToCell(-16 + sprite.getSize() / 2 + coordinateY);
        } else {
            u += Coordinates.pixelToCell(coordinateX) + (int) x;
            v += Coordinates.pixelToCell(-16 + coordinateY) + (int) y;
        }
        Entity a = board.getEntity(u, v, this);

        return a.collide(this);
    }

    /**
     * Check collision with other entity.
     *
     * @param e entity
     * @return boolean true or false
     */
    @Override
    public boolean collide(Entity e) {
        if (e instanceof Flare || e instanceof FlareSegment) {
            killAndRemove();

            return false;
        }
        if (e instanceof Bomber) {
            if (!collisionWith((Character) e)) {
                return true;
            }
            ((Bomber) e).killAndRemove();

            return false;
        }
        return true;
    }

    /**
     * override function to killAndRemove.
     */
    @Override
    public void killAndRemove() {
        if (!alive) {
            return;
        }
        alive = false;
        board.addPoints(points);
        Message msg = new Message("+" + points, getXMessage(), getYMessage(), 120, Color.white, 14);

        board.addMessage(msg);
        enemyDieSound.play();
    }

    /**
     * Override function after removal.
     */
    @Override
    protected void afterRemoval() {
        if (timeAfter > 0) {
            timeAfter--;
        } else {
            if (finalAnimation > 0) {
                finalAnimation--;
            } else {
                remove();
            }
        }
    }

    protected abstract void chooseSprite();
}