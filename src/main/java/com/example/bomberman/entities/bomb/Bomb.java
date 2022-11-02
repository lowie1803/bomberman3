/**
 * Class Bomb
 *
 * @author low_
 */

package com.example.bomberman.entities.bomb;

import com.example.bomberman.Board;
import com.example.bomberman.Game;
import com.example.bomberman.LoadMap.Coordinates;
import com.example.bomberman.entities.AnimatedEntity;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.character.Bomber;
import com.example.bomberman.entities.character.Character;
import com.example.bomberman.graphics.Screen;
import com.example.bomberman.graphics.Sprite;

public class Bomb extends AnimatedEntity {
    private double timeRemainToExplode = 100;
    private boolean exploded = false;
    private boolean canBePassed = true;
    private int timeExploding = 20;
    private final Board board;
    private Flare[] flares;

    public Bomb(int x, int y, Board board) {
        coordinateX = x;
        coordinateY = y;
        this.board = board;
        sprite = Sprite.bomb;

    }

    /**
     * Update bomb status.
     */
    @Override
    public void update() {
        if (timeRemainToExplode > 0)
            timeRemainToExplode--;
        else {
            if (!exploded)
                explode();
            else
                updateFlares();

            if (timeExploding > 0)
                timeExploding--;
            else
                remove();
        }
        animate();
    }

    /**
     * Render Bomb to Screen.
     * @param screen screen
     */
    @Override
    public void render(Screen screen) {
        if (exploded) {
            sprite = Sprite.bomb_exploded2;
            renderFlares(screen);
        } else
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 30);

        int xt = (int) (coordinateX * Game.CELLS_SIZE);
        int yt = (int) (coordinateY * Game.CELLS_SIZE);

        screen.renderEntity(xt, yt, this);
    }

    /**
     * Render Flares to Screen.
     * @param screen screen
     */
    public void renderFlares(Screen screen) {
        for (Flare flare : flares) {
            flare.render(screen);
        }
    }

    /**
     * Update flare
     */
    public void updateFlares() {
        for (Flare flare : flares) {
            flare.update();
        }
    }

    /**
     * Solve Bomb explode.
     */
    protected void explode() {
        exploded = true;
        canBePassed = true;
        Character a = board.getCharacterAt(coordinateX, coordinateY);
        if (a != null) a.killAndRemove();
        flares = new Flare[4];
        for (int i = 0; i < flares.length; i++) {
            flares[i] = new Flare((int) coordinateX, (int) coordinateY, i, Game.getBombRadius(), board);
        }
        Board.explosionSound.play();
    }

    public FlareSegment flareAt(int x, int y) {
        if (!exploded) return null;

        for (Flare flare : flares) {
            if (flare == null) return null;
            FlareSegment e = flare.flareSegmentAt(x, y);
            if (e != null) return e;
        }

        return null;
    }

    /**
     * Handle collision.
     * @param e entity
     * @return boolean
     */
    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            double diffX = e.getX() - Coordinates.cellToPixel(getX());
            double diffY = e.getY() - Coordinates.cellToPixel(getY());

            if (!(diffX >= -10 && diffY >= 1 && diffX < 16 && diffY <= 28)) {
                canBePassed = false;
            }

            return canBePassed;
        }

        if (e instanceof FlareSegment || e instanceof Flare) {
            timeRemainToExplode = 0;
            return true;
        }

        return false;
    }
}
