/**
 * Class LevelLoader
 *
 * @author low_
 */
package com.example.bomberman.LoadMap;

import com.example.bomberman.Board;

public abstract class LevelLoader {
    protected int width = 20, height = 20; // default values just for testing
    protected int level;
    protected Board board;

    public LevelLoader(Board board, int level) {
        this.board = board;
        loadLevel(level);
    }

    public abstract void loadLevel(int level);

    public abstract void createEntities();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
