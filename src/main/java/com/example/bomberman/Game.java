/**
 * Class Game
 *
 * @author low_
 */

package com.example.bomberman;

import com.example.bomberman.graphics.Screen;
import com.example.bomberman.gui.Frame;
import com.example.bomberman.kb.Keyboard;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * Initiate game loop. Handle game initial global constants.
 * Handle calls to render() and update() to all entities.
 */
public class Game extends Canvas {
    public static final int CELLS_SIZE = 16;
    public static final int WIDTH = CELLS_SIZE * (31 / 2);
    public static final int HEIGHT = 13 * CELLS_SIZE;
    public static int SCALE = 3;
    public static final String TITLE = "Bomberman by low_";
    public static final int STARTING_BOMB_COUNT = 1;
    public static final int STARTING_BOMB_RADIUS = 1;
    public static final double STARTING_BOMBER_SPEED = 1.0;
    public static final int TIME = 200;
    public static final int POINTS = 0;
    public static final int LIVES = 3;
    public static final int SCREEN_DELAY = 3;
    protected static int bombCount = STARTING_BOMB_COUNT;
    protected static int bombRadius = STARTING_BOMB_RADIUS;
    protected static double bomberSpeed = STARTING_BOMBER_SPEED;
    protected static int levelAddedBombCount = 0;
    protected static int levelAddedBombRadius = 0;
    protected static double levelAddedBomberSpeed = 0;
    protected int screenDelay = SCREEN_DELAY;
    private final Keyboard input;
    private boolean running = false;
    private boolean paused = true;
    private final Board board;
    private final Screen screen;
    private final Frame frame;
    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    public Game(Frame frame) {
        this.frame = frame;
        this.frame.setTitle(TITLE);
        screen = new Screen(WIDTH, HEIGHT);
        input = new Keyboard();
        board = new Board(this, input, screen);
        addKeyListener(input);
    }

    private void renderGame() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        screen.clear();
        board.render(screen);
        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);
        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        board.renderMessages(g);
        g.dispose();
        bs.show();
    }

    private void renderScreen() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        screen.clear();
        Graphics g = bs.getDrawGraphics();

        board.drawScreen(g);
        g.dispose();
        bs.show();
    }

    private void update() {
        input.update();
        board.update();
    }

    public void start() {
        running = true;

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;

        requestFocus();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            if (paused) {
                if (screenDelay <= 0) {
                    board.setShow(-1);
                    paused = false;
                    Board.backgroundSound.play();
                }
                renderScreen();
            } else {
                renderGame();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                frame.setTime(board.subtractTime());
                frame.setPoints(board.getPoints());
                frame.setLives(board.getLives());
                frame.setHighScore(board.getHighScore());
                timer += 1000;
                frame.setTitle(TITLE + " " + updates + " rate, " + frames + " fps");
                updates = 0;
                frames = 0;
                if (board.getShow() == 2) {
                    screenDelay--;
                }
            }
        }
    }

    public static double getBomberSpeed() {
        return bomberSpeed;
    }

    public static int getStartingBombCount() {
        return bombCount;
    }

    public static int getBombRadius() {
        return bombRadius;
    }

    /**
     * Increase speed of bomber.
     *
     * @param speed_gain speed gain
     */
    public static void addBomberSpeed(double speed_gain) {
        levelAddedBomberSpeed += speed_gain;
    }

    /**
     * Increase radius of bomb.
     *
     * @param radius_gain radius gain
     */
    public static void addBombRadius(int radius_gain) {
        levelAddedBombRadius += radius_gain;

    }

    /**
     * Increase bombers' bomb count.
     *
     * @param bomb_count bomb count.
     */
    public static void addBombCount(int bomb_count) {
        levelAddedBombCount += bomb_count;
    }

    /**
     * Reset all after restart level.
     */
    public static void resetLevel() {
        levelAddedBombCount = 0;
        levelAddedBomberSpeed = 0;
        levelAddedBombRadius = 0;
    }

    public static void newGameReset() {
        bombCount = STARTING_BOMB_COUNT;
        bombRadius = STARTING_BOMB_RADIUS;
        bomberSpeed = STARTING_BOMBER_SPEED;
        resetLevel();
    }

    public static void nextLevelReset() {
        bombCount += levelAddedBombCount;
        bombRadius += levelAddedBombRadius;
        bomberSpeed += levelAddedBomberSpeed;
        resetLevel();
    }

    public void resetScreenDelay() {
        screenDelay = SCREEN_DELAY;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isPaused() {
        return paused;
    }


    public void pause() {
        paused = true;
    }

    public void run() {
        running = true;
        paused = false;
    }
}