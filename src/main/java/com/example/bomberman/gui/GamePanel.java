package com.example.bomberman.gui;
/**
 * Class GamePanel
 *
 * @author low_
 */

import com.example.bomberman.Game;

import javax.swing.*;
import java.awt.Frame;
import java.awt.*;

public class GamePanel extends JPanel {

    private final Game game;

    /**
     * Constructor.
     * @param frame frame
     */
    public GamePanel(Frame frame) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

        game = new Game((com.example.bomberman.gui.Frame) frame);

        add(game);

        game.setVisible(true);

        setVisible(true);
        setFocusable(true);

    }

    /**
     * return game.
     * @return game
     */
    public Game getGame() {
        return game;
    }

}
