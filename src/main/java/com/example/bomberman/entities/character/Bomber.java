/**
 * @author low_
 */

package com.example.bomberman.entities.character;

import com.example.bomberman.Board;
import com.example.bomberman.Game;
import com.example.bomberman.LoadMap.Coordinates;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.bomb.Bomb;
import com.example.bomberman.entities.bomb.Flare;
import com.example.bomberman.entities.bomb.FlareSegment;
import com.example.bomberman.entities.character.enemy.Enemy;
import com.example.bomberman.graphics.Screen;
import com.example.bomberman.graphics.Sprite;
import com.example.bomberman.kb.Keyboard;

import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {
    private final List<Bomb> bombs;
    private final Keyboard input;

    private int timeBetweenPutBombs = 0;

    /**
     * Basic constructor.
     *
     * @param x     int x
     * @param y     int y
     * @param board board
     */
    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        bombs = board.getBombs();
        input = board.getInput();
        sprite = Sprite.player_right;
    }

    /**
     * Update game.
     */
    @Override
    public void update() {
        clearBombs();
        if (!alive) {
            afterRemoval();

            return;
        }
        if (timeBetweenPutBombs < -3000) {
            timeBetweenPutBombs = 0;
        } else {
            --timeBetweenPutBombs;
        }
        animate();
        calculateMove();
        detectPlaceBomb();
    }

    /**
     * override function to render screen.
     *
     * @param screen screen
     */
    @Override
    public void render(Screen screen) {
        calculateXOffset();
        if (alive) {
            chooseSprite();
        } else {
            sprite = Sprite.player_dead1;
        }
        screen.renderEntity((int) coordinateX, (int) coordinateY - sprite.SIZE, this);
    }

    /**
     * to chia man hinh.
     */
    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(board, this);

        Screen.setOffset(xScroll, 0);
    }

    private void detectPlaceBomb() {
        if (input.space && timeBetweenPutBombs < 0 && Game.getStartingBombCount() > 0) {
            int x1 = Coordinates.pixelToCell(sprite.getSize() / 2 + coordinateX);
            int y1 = Coordinates.pixelToCell(coordinateY - sprite.getSize() / 1.5);

            placeBomb(x1, y1);
            Game.addBombCount(-1);
            timeBetweenPutBombs = 30;
            Board.placeBombSound.play();
        }
    }

    /**
     * dat bom vao vi tri x, y.
     *
     * @param x int x
     * @param y int y
     */
    protected void placeBomb(int x, int y) {
        Bomb b = new Bomb(x, y, board);

        board.addBomb(b);
    }

    /**
     * to clear bombs.
     */
    private void clearBombs() {
        Iterator<Bomb> bs = bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombCount(1);
            }
        }
    }

    /**
     * to kill character.
     */
    @Override
    public void killAndRemove() {
        if (!alive) {
            return;
        }
        alive = false;
        Board.bomberDieSound.play();
    }

    /**
     * Update after killing.
     */
    @Override
    protected void afterRemoval() {
        if (timeAfter > 0) {
            timeAfter--;
        } else {
            board.restartLevel();
        }
    }

    /**
     * Calculate direction to move.
     */
    @Override
    protected void calculateMove() {
        int x1 = 0, y1 = 0;

        if (input.left) {
            x1--;
        }
        if (input.right) {
            x1++;
        }
        if (input.down) {
            y1++;
        }
        if (input.up) {
            y1--;
        }
        if (x1 != 0 || y1 != 0) {
            move(x1 * Game.getBomberSpeed(), y1 * Game.getBomberSpeed());
            moving = true;
        } else {
            moving = false;
        }
    }

    /**
     * Check whether it's possible to move to (x, y)
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return true if movable
     */
    @Override
    public boolean movable(double x, double y) {
        for (int c = 0; c < 4; ++c) {
            double x1 = ((coordinateX + x) + c % 2 * 11) / Game.CELLS_SIZE;
            double y1 = ((coordinateY + y) + c / 2 * 12 - 13) / Game.CELLS_SIZE;
            Entity a = board.getEntity(x1, y1, this);

            if (a != null) {
                if (!a.collide(this)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * to move to x, y position.
     *
     * @param u double xa
     * @param v double ya
     */
    @Override
    public void move(double u, double v) {
        if (u < 0) {
            direction = 3;
        }
        if (u > 0) {
            direction = 1;
        }
        if (v < 0) {
            direction = 0;
        }
        if (v > 0) {
            direction = 2;
        }
        if (movable(0, v)) {
            coordinateY += v;
        }
        if (movable(u, 0)) {
            coordinateX += u;
        }
    }

    /**
     * Handle collision.
     *
     * @param e Entity
     * @return true or false
     */
    @Override
    public boolean collide(Entity e) {
        if (e instanceof FlareSegment || e instanceof Flare) {
            killAndRemove();

            return false;
        }
        if (e instanceof Enemy) {
            if (collisionWith((Character) e)) {
                killAndRemove();
            }
            return true;
        }
        return true;
    }

    private void chooseSprite() {
        switch (direction) {
            case 0:
                sprite = Sprite.player_up;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.player_right;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
            case 2:
                sprite = Sprite.player_down;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
                break;
            case 3:
                sprite = Sprite.player_left;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
                break;
        }
    }
}