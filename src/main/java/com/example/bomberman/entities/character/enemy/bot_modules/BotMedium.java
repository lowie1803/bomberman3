/**
 * @author low_
 */

package com.example.bomberman.entities.character.enemy.bot_modules;

import com.example.bomberman.Board;
import com.example.bomberman.entities.character.Bomber;
import com.example.bomberman.entities.character.enemy.Enemy;
import javafx.util.Pair;

import java.util.LinkedList;

public class BotMedium extends Bot {
    Bomber bomber;
    Enemy e;
    Board board;
    public static final int inf = 444444444;
    public static final int[] dirX = new int[4];
    public static final int[] dirY = new int[4];

    /**
     * Constructor.
     *
     * @param bomber bomber
     * @param e      enemy
     * @param board  board
     */
    public BotMedium(Bomber bomber, Enemy e, Board board) {
        this.bomber = bomber;
        this.e = e;
        this.board = board;
        dirX[0] = 0;
        dirY[0] = -1;
        dirX[1] = 1;
        dirY[1] = 0;
        dirX[2] = 0;
        dirY[2] = 1;
        dirX[3] = -1;
        dirY[3] = 0;
    }

    /**
     * Override function to calculate direction.
     *
     * @return int direction
     */
    @Override
    public int nextDirection() {
        Pair<Integer, Integer> temp = shortestPath(bomber.getXCell(), bomber.getYCell());
        if (temp.getKey() > 6) {
            return -1;
        }

        return temp.getValue();
    }

    /**
     * Check invalid position.
     *
     * @param posX int pos x
     * @param posY int pos y
     * @return true if collide
     */
    public boolean isInvalidPosition(int posX, int posY) {
        if (posX < 0 || posY < 0 || posX >= board.getWidth() || posY >= board.getHeight()) {
            return false;
        }
        return board.getEntityAt(posX, posY).collide(e);
    }

    /**
     * BFS algorithm to find the shortest path to bomber.
     *
     * @param startX start x
     * @param startY start y
     * @param endX   end x
     * @param endY   end y
     * @return distance
     */
    private int bfs(int startX, int startY, int endX, int endY) {
        if (startX == endX && startY == endY) {
            return 0;
        }
        int m = board.getHeight();
        int n = board.getWidth();
        boolean[][] passed = new boolean[n][m];
        LinkedList<Pair<Pair<Integer, Integer>, Integer>> q = new LinkedList<>();

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                passed[i][j] = false;
            }
        }
        passed[startX][startY] = true;
        q.add(new Pair<>(new Pair<>(startX, startY), 0));
        while (!q.isEmpty()) {
            Pair<Pair<Integer, Integer>, Integer> top = q.getFirst();

            q.remove(0);
            for (int i = 0; i < 4; ++i) {
                int x = dirX[i] + top.getKey().getKey();
                int y = dirY[i] + top.getKey().getValue();

                if (isInvalidPosition(x, y) && !passed[x][y]) {
                    if (x == endX && y == endY) {
                        return 1 + top.getValue();
                    }
                    q.add(new Pair<>(new Pair<>(x, y), 1 + top.getValue()));
                    passed[x][y] = true;
                }
            }
        }

        return inf;
    }

    /**
     * to find all direction and prepare to find the shortest path.
     *
     * @param endX end x
     * @param endY end y
     * @return pair
     */
    private Pair<Integer, Integer> shortestPath(int endX, int endY) {
        int dir = -1;
        int res = inf;

        for (int i = 0; i < 4; i++) {
            int x = dirX[i] + e.getXCell();
            int y = dirY[i] + e.getYCell();

            if (isInvalidPosition(x, y)) {
                int temp = bfs(x, y, endX, endY);

                if (temp < res) {
                    res = temp;
                    dir = i;
                }
            }
        }

        return new Pair<>(res, dir);
    }
}