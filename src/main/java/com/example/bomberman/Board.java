/**
 * Class Board
 *
 * @author low_
 */

package com.example.bomberman;

import com.example.bomberman.LoadMap.FileLevelLoader;
import com.example.bomberman.LoadMap.LevelLoader;
import com.example.bomberman.entities.Entity;
import com.example.bomberman.entities.LayeredEntity;
import com.example.bomberman.entities.Message;
import com.example.bomberman.entities.bomb.Bomb;
import com.example.bomberman.entities.bomb.FlareSegment;
import com.example.bomberman.entities.cell.Wall;
import com.example.bomberman.entities.cell.destroyable.Brick;
import com.example.bomberman.entities.character.Bomber;
import com.example.bomberman.entities.character.Character;
import com.example.bomberman.graphics.IRender;
import com.example.bomberman.graphics.Screen;
import com.example.bomberman.kb.Keyboard;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Quản lý thao tác điều khiển, load level, render các màn hình của game
 */
public class Board implements IRender {

    public Entity[] entities;
    public List<Character> characters = new ArrayList<>();
    public static SoundPlayer levelUpSound = new SoundPlayer(new File("res/sound/levelUp.wav"));
    public static SoundPlayer backgroundSound = new SoundPlayer(new File("res/sound/bgmusic.wav"));
    public static SoundPlayer gameOverSound = new SoundPlayer(new File("res/sound/gameOver.wav"));
    public static SoundPlayer victorySound = new SoundPlayer(new File("res/sound/victory.wav"));
    public static SoundPlayer explosionSound = new SoundPlayer(new File("res/sound/explosion.wav"));
    public static SoundPlayer placeBombSound = new SoundPlayer(new File("res/sound/placeBomb.wav"));
    public static SoundPlayer bomberDieSound = new SoundPlayer(new File("res/sound/bomberDie.wav"));
    public static SoundPlayer getItemSound = new SoundPlayer(new File("res/sound/getItem.wav"));

    protected LevelLoader levelLoader;
    protected Game game;
    protected Keyboard input;
    protected Screen screen;
    protected List<Bomb> bombs = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();
    private int screenToShow = -1; //1:endgame, 2:changelevel, 3:paused, 4: startgame,

    private int time = Game.TIME;
    private int points = Game.POINTS;
    private int lives = Game.LIVES;
    private int plusPoint = 0;
    private int highScore = 0;

    private boolean victory = false;
    private double lastUpdatePaused = 0;

    /**
     * Constructor.
     */
    public Board(Game game, Keyboard input, Screen screen) {
        this.game = game;
        this.input = input;
        this.screen = screen;
        readHighScore();
        loadLevel(1);
    }

    /**
     * write high score to file.
     *
     * @throws IOException when error on open file
     */
    private void writeHighScore() throws IOException {
        FileWriter w = new FileWriter(new File("res/userData/highScore.txt"));
        w.write(String.valueOf(highScore));
        w.close();
    }

    /**
     * read high score from file.
     */
    private void readHighScore() {
        Scanner sc = null;
        File fileObj = new File("res/userData/highScore.txt");
        try {
            sc = new Scanner(fileObj);
            highScore = sc.nextInt();
            sc.close();
        } catch (FileNotFoundException e) {
            try {
                if (!fileObj.getParentFile().exists())
                    fileObj.getParentFile().mkdirs();
                if (fileObj.createNewFile()) {
                    System.out.println("File created: " + fileObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException ee) {
                System.out.println("An error occurred.");
                ee.printStackTrace();
            }
            try {
                FileWriter writerObj = new FileWriter("res/userData/highScore.txt");
                writerObj.write("0");
                writerObj.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException ee) {
                System.out.println("An error occurred.");
                ee.printStackTrace();
            }
            highScore = 0;
        }
    }

    @Override
    public void update() {
        if (screenToShow == 1 && input.space) {
            newGame();
        }
        if (input.esc && System.currentTimeMillis() - lastUpdatePaused > 500) {
            if (game.isPaused()) gameResume();
            else gamePause();
            lastUpdatePaused = System.currentTimeMillis();
        }
        if (game.isPaused()) return;
        updateEntities();
        updateCharacters();
        updateBombs();
        updateMessages();
        detectEndGame();
        characters.removeIf(Entity::isRemoved);
    }

    @Override
    public void render(Screen screen) {
        if (game.isPaused()) return;

        int xLeft = Screen.xOffset >> 4;
        int yUp = Screen.yOffset >> 4;
        int xRight = (Screen.xOffset + screen.getWidth() + Game.CELLS_SIZE) / Game.CELLS_SIZE;
        int yDown = (Screen.yOffset + screen.getHeight()) / Game.CELLS_SIZE;
        for (int y = yUp; y < yDown; y++) {
            for (int x = xLeft; x < xRight; x++) {
                entities[x + y * levelLoader.getWidth()].render(screen);
            }
        }
        renderBombs(screen);
        renderCharacter(screen);
    }

    public void gamePause() {
        game.resetScreenDelay();
        backgroundSound.stop();
        if (screenToShow <= 0)
            screenToShow = 3;
        game.pause();
    }

    public void gameResume() {
        game.resetScreenDelay();
        backgroundSound.play();
        screenToShow = -1;
        game.run();
    }

    public void loadLevel(int level) {
        time = Game.TIME;
        screenToShow = 2;
        game.resetScreenDelay();
        game.pause();
        characters.clear();
        bombs.clear();
        messages.clear();
        try {
            writeHighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (level > 5) {
            victory = true;
            endGame();
            return;
        }
        levelLoader = new FileLevelLoader(this, level);
        entities = new Entity[levelLoader.getHeight() * levelLoader.getWidth()];
        levelLoader.createEntities();
        backgroundSound.stop();
        gameOverSound.stop();
        victorySound.stop();
        levelUpSound.play();
    }

    public void nextLevel() {
        Game.addBombCount(bombs.size());
        bombs.clear();
        addPoints(time);
        plusPoint = 0;
        Game.nextLevelReset();
        loadLevel(levelLoader.getLevel() + 1);
    }

    public void restartLevel() {
        lives--;
        if (lives == 0) endGame();
        else {
            this.points -= this.plusPoint;
            this.plusPoint = 0;
            Game.resetLevel();
            loadLevel(levelLoader.getLevel());
        }
    }

    protected void detectEndGame() {
        if (time < 0) {
            restartLevel();
        }
    }

    public void newGame() {
        lives = Game.LIVES;
        victory = false;
        points = Game.POINTS;
        Game.bombRadius = Game.STARTING_BOMB_RADIUS;
        Game.bombCount = Game.STARTING_BOMB_COUNT;
        Game.bomberSpeed = Game.STARTING_BOMBER_SPEED;
        plusPoint = 0;
        Game.newGameReset();
        loadLevel(1);
    }

    public void endGame() {
        screenToShow = 1;
        game.resetScreenDelay();
        game.pause();
        backgroundSound.stop();
        if (victory)
            victorySound.play();
        else
            gameOverSound.play();
    }

    public boolean detectNoEnemies() {
        for (Character c : characters) {
            if (!(c instanceof Bomber))
                return false;
        }
        return true;
    }

    public void drawScreen(Graphics g) {
        switch (screenToShow) {
            case 1:
                if (victory)
                    screen.drawVictory(g, points);
                else
                    screen.drawDefeat(g, points);
                break;
            case 2:
                screen.drawChangeLevel(g, levelLoader.getLevel());
                break;
            case 3:
                screen.drawPaused(g);
                break;
        }
    }

    public Entity getEntity(double x, double y, Character m) {
        Entity res;

        res = getFlareSegmentAt((int) x, (int) y);
        if (res != null) return res;

        res = getBombAt(x, y);
        if (res != null) return res;

        res = getCharacterAtExcluding((int) x, (int) y, m);
        if (res != null) return res;

        res = getEntityAt((int) x, (int) y);
        return res;
    }

    public boolean isWallBrick(int x, int y) {
        Entity e = entities[x + y * levelLoader.getWidth()];
        if (e instanceof LayeredEntity) {
            return ((LayeredEntity) e).getTopEntity() instanceof Brick;
        }
        return (e instanceof Brick || e instanceof Wall);

    }

    public Character getCharacterAt(double x, double y) {
        for (Character cur : characters) {
            if (cur.getXCell() == x && cur.getYCell() == y)
                return cur;
        }
        return null;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public Bomb getBombAt(double x, double y) {
        for (Bomb b : bombs) {
            if (b.getX() == (int) x && b.getY() == (int) y)
                return b;
        }
        return null;
    }

    public Bomber getBomber() {
        for (Character cur : characters) {
            if (cur instanceof Bomber)
                return (Bomber) cur;
        }

        return null;
    }

    public Character getCharacterAtExcluding(int x, int y, Character a) {
        for (Character character : characters) {
            if (character == a) {
                continue;
            }
            if (character.getXCell() == x && character.getYCell() == y) {
                return character;
            }
        }
        return null;
    }

    public FlareSegment getFlareSegmentAt(int x, int y) {
        for (Bomb b : bombs) {
            FlareSegment flareSegment = b.flareAt(x, y);
            if (flareSegment != null) {
                return flareSegment;
            }
        }
        return null;
    }

    public Entity getEntityAt(int x, int y) {
        return entities[x + y * levelLoader.getWidth()];
    }

    public void addEntity(int pos, Entity e) {
        entities[pos] = e;
    }

    public void addCharacter(Character e) {
        characters.add(e);
    }

    public void addBomb(Bomb e) {
        bombs.add(e);
    }

    public void addMessage(Message e) {
        messages.add(e);
    }

    protected void renderCharacter(Screen screen) {
        for (Character character : characters) {
            character.render(screen);
        }
    }

    protected void renderBombs(Screen screen) {
        for (Bomb b : bombs) {
            b.render(screen);
        }
    }

    public void renderMessages(Graphics g) {
        for (Message m : messages) {
            g.setFont(Screen.MAIN_FONT);
            g.setColor(m.getColor());
            g.drawString(m.getText(), (int) m.getX() - Screen.xOffset * Game.SCALE, (int) m.getY());
        }
    }

    protected void updateEntities() {
        if (game.isPaused()) return;
        for (Entity entity : entities) {
            entity.update();
        }
    }

    protected void updateCharacters() {
        if (game.isPaused()) return;
        Character bomber = null;
        for (Character character : characters) {
            if (game.isPaused()) {
                break;
            }
            if ((character instanceof Bomber)) bomber = character;
        }
        if (bomber != null) bomber.update();
        for (Character character : characters) {
            if (game.isPaused()) {
                break;
            }
            if (!(character instanceof Bomber)) character.update();
        }
    }

    protected void updateBombs() {
        if (game.isPaused()) return;
        for (Bomb b : bombs) {
            b.update();
        }
    }

    protected void updateMessages() {
        if (game.isPaused()) return;
        Message m;
        int left;
        for (int i = 0; i < messages.size(); i++) {
            m = messages.get(i);
            left = m.getInterval();

            if (left > 0) {
                m.setInterval(--left);
            } else {
                messages.remove(i);
            }
        }
    }

    public int subtractTime() {
        if (game.isPaused())
            return this.time;
        else
            return this.time--;
    }

    public Keyboard getInput() {
        return input;
    }

    public LevelLoader getLevel() {
        return levelLoader;
    }

    public Game getGame() {
        return game;
    }

    public int getShow() {
        return screenToShow;
    }

    public void setShow(int i) {
        screenToShow = i;
    }

    public int getTime() {
        return time;
    }

    public int getPoints() {
        return points;
    }

    public int getLives() {
        return lives;
    }

    public int getHighScore() {
        return highScore;
    }

    public void addPoints(int points) {
        this.plusPoint += points;
        this.points += points;
        if (highScore < points) {
            highScore = points;
        }

    }

    public int getWidth() {
        return levelLoader.getWidth();
    }

    public int getHeight() {
        return levelLoader.getHeight();
    }
}
