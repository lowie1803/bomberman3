/**
 * Class FileLevelLoader
 *
 * @author low_
 */
package com.example.bomberman.LoadMap;

import com.example.bomberman.Board;
import com.example.bomberman.Game;
import com.example.bomberman.entities.LayeredEntity;
import com.example.bomberman.entities.cell.Grass;
import com.example.bomberman.entities.cell.Portal;
import com.example.bomberman.entities.cell.Wall;
import com.example.bomberman.entities.cell.destroyable.Brick;
import com.example.bomberman.entities.cell.item.BombItem;
import com.example.bomberman.entities.cell.item.FlareItem;
import com.example.bomberman.entities.cell.item.SpeedItem;
import com.example.bomberman.entities.character.Bomber;
import com.example.bomberman.entities.character.enemy.Balloon;
import com.example.bomberman.entities.character.enemy.Oneal;
import com.example.bomberman.graphics.Screen;
import com.example.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLevelLoader extends LevelLoader {
    private static String[] map;

    public FileLevelLoader(Board board, int level) {
        super(board, level);
    }

    @Override
    public void loadLevel(int level) {
        try {
            Scanner sc = new Scanner(new File("res/levels/Level" + level + ".txt"));
            this.level = sc.nextInt();
            height = sc.nextInt();
            width = sc.nextInt();
            map = new String[height];
            map[0] = sc.nextLine();
            for (int i = 0; i < height; i++) {
                map[i] = sc.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createEntities() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                addSingleEntity(map[y].charAt(x), x, y);
            }
        }

    }

    /**
     * Add entities
     * @param c character denotes the entity of the sprite
     * @param x x-coordinates
     * @param y y-coordinates
     */
    public void addSingleEntity(char c, int x, int y) {
        int pos = x + y * width;
        switch (c) {
            case '#':
                board.addEntity(pos, new Wall(x, y, Sprite.wall));
                break;
            case 'p':
                board.addCharacter(new Bomber(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                Screen.setOffset(0, 0);
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            case '*':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Brick(x, y, Sprite.brick)));
                break;
            case 'b':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new BombItem(x, y, Sprite.powerup_bombs), new Brick(x, y, Sprite.brick)));
                break;
            case 'f':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new FlareItem(x, y, Sprite.powerup_flares), new Brick(x, y, Sprite.brick)));
                break;
            case 's':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick)));
                break;
            case 'x':
                board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new Portal(x, y, Sprite.portal, board), new Brick(x, y, Sprite.brick)));
                break;
            case '1':
                board.addCharacter(new Balloon(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            case '2':
                board.addCharacter(new Oneal(Coordinates.cellToPixel(x), Coordinates.cellToPixel(y) + Game.CELLS_SIZE, board));
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
            default:
                board.addEntity(pos, new Grass(x, y, Sprite.grass));
                break;
        }
    }
}

