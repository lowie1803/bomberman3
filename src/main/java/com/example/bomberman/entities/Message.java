/**
 * @author low_
 */
package com.example.bomberman.entities;

import com.example.bomberman.graphics.Screen;

import java.awt.*;

public class Message extends Entity {
    private final String text;
    private final Color color;
    private final int size;
    private int interval;

    /**
     * Constructor.
     *
     * @param text               content of text
     * @param x                  x-coordinate
     * @param y                  y-coordinate
     * @param interval_in_second how long message stays on screen
     * @param color              color of message
     * @param size               size of message
     */
    public Message(String text, double x, double y, int interval_in_second, Color color, int size) {
        coordinateX = x;
        coordinateY = y;
        this.text = text;
        this.interval = interval_in_second;
        this.color = color;
        this.size = size;
    }

    public int getInterval() {
        return interval;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Screen screen) {
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }
}
