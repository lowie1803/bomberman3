/**
 * @author low_
 */

package com.example.bomberman.entities.character.enemy.bot_modules;

public class Characteristic {
    private final int coordinateX;
    private final int coordinateY;
    private final int distance;

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public int getDistance() {
        return distance;
    }

    /**
     * constructor.
     *
     * @param coordinateX coordinate x1
     * @param coordinateY coordinate y1
     * @param distance    dis1
     */
    public Characteristic(int coordinateX, int coordinateY, int distance) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.distance = distance;
    }
}